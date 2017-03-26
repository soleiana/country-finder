package com.demo.reader_pipeline.core

import com.demo.reader_pipeline.exceptions.FormatException
import spock.lang.Specification

class RawNumberStringSpec extends Specification {

    static final EMPTY_NUMBER = 'phone number is empty'
    RawNumberString phoneNumberString


    def "should remove white space characters from phone number"() {

        given: "non empty phone number"
            phoneNumberString = createRawNumberString(phoneNumber)

        when: "remove space characters"
            def formattedPhoneNumber = phoneNumberString.withoutSpaceCharacters()

        then: "create phone number without space characters"
            formattedPhoneNumber == createRawNumberString(expectedPhoneNumber)
        where:
            phoneNumber             || expectedPhoneNumber
            '+371 12345678'         || '+37112345678'
            '\t371 12345678 '       || '37112345678'
            '\t371 12345678\t'      || '37112345678'
            '+  371 \n12345678'     || '+37112345678'
            ' +371 12345678'        || '+37112345678'
            '\n\t 1 (123) 456 7890' || '1(123)4567890'
            '\t \n  \t\t'           || ''
            ' '                     || ''
    }

    def "should add international call prefix (if missing) to phone number"() {

        given: "non empty phone number"
            phoneNumberString = createRawNumberString(phoneNumber)

        when: "add call prefix"
            def formattedPhoneNumber = phoneNumberString.withInternationalCallPrefix()

        then: "create phone number with prefix"
            formattedPhoneNumber == createRawNumberString(expectedPhoneNumber)

        where:
            phoneNumber             || expectedPhoneNumber
            '+371 12345678'         || '+371 12345678'
            '371 12345678'          || '+371 12345678'
            ' 371 12345678'         || '+ 371 12345678'
            '\t371 12345678\t'      || '+\t371 12345678\t'
            '  '                     || '+  '
            ''                      || '+'
            '+'                     || '+'
    }

    def "should throw FormatException if invalid phone number"() {

        given: "phone number is empty | containing white space characters | non-numeric phone number characters"
            phoneNumberString = createRawNumberString(phoneNumber)

        when: "check format"
            phoneNumberString.checkFormat()

        then: "FormatException thrown"
            def exception = thrown(FormatException)
            exception.message == EMPTY_NUMBER

        where:
            phoneNumber     | _
            '+371 12345678' | _
            ''              | _
            ' '             | _
            '\t \n'         | _
            '+(.)--'        | _
            ' + ..((x'      | _
            '+'             | _
    }

    def createRawNumberString(String phoneNumber) {
        new RawNumberString(phoneNumber)
    }

}
