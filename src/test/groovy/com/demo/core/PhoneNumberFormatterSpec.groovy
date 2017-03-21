package com.demo.core

import com.demo.exceptions.FormatException
import spock.lang.Specification

class PhoneNumberFormatterSpec extends Specification {

    final EMPTY_NUMBER_MESSAGE = "phone number is empty"
    PhoneNumberFormatter phoneNumberFormatter =  new PhoneNumberFormatter()

    def "should return formatted phone number"() {

        given: "non empty phone number"
            def expectedPhoneNumber = phoneNumberFormatter.apply(phoneNumber)

        expect: "space normalized phone number with the leading plus"
            expectedPhoneNumber == formattedPhoneNumber
        where:
            phoneNumber             || formattedPhoneNumber
            '+371 12345678'         || '+371 12345678'
            '371  123\t  45  678'   || '+371 123 45 678'
            ' 371 12345678 '        || '+371 12345678'
            '\t371 12345678\t'      || '+371 12345678'
            '+  371 12345678'       || '+ 371 12345678'
            ' +371 12345678'        || '+371 12345678'
    }

    def "should throw FormatException if empty phone number"() {

        when: "there is empty, containing only space chars, and/or '+' phone number"
            phoneNumberFormatter.apply(phoneNumber)

        then: "expect FormatException"
            def exception = thrown(FormatException)
            exception.message == EMPTY_NUMBER_MESSAGE

        where:
            phoneNumber     | _
            ''              | _
            ' '             | _
            '   '           | _
            '+'             | _
            ' + '           | _
            ' \t '          | _
    }
}
