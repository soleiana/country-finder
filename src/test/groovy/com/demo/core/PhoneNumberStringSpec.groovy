package com.demo.core

import com.demo.exceptions.FormatException
import spock.lang.Specification

class PhoneNumberStringSpec extends Specification {

    static final EMPTY_NUMBER_MESSAGE = 'phone number is empty'

    PhoneNumberString phoneNumberString

    def "should format phone number for final validation"() {

        given: "non empty phone number"
            phoneNumberString = new PhoneNumberString(phoneNumber)

        when: "format for final validation"
            def formattedPhoneNumber = phoneNumberString.formatForFinalValidation()

        then: "create stripped of space characters phone number with the leading plus"
            expectedPhoneNumber == formattedPhoneNumber
        where:
            phoneNumber             || expectedPhoneNumber
            '+371 12345678'         || '+37112345678'
            '371  123\t  45  678'   || '+37112345678'
            ' 371 12345678 '        || '+37112345678'
            '\t371 12345678\t'      || '+37112345678'
            '+  371 12345678'       || '+37112345678'
            ' +371 12345678'        || '+37112345678'
            '+1 (123)   456 7890'   || '+1(123)4567890'
            '+1 - (123) 456 7890'   || '+1-(123)4567890'
            '+1-( 123 ) 4567890'    || '+1-(123)4567890'
            '+1 .  123456.7890'     || '+1.123456.7890'
            '+123 .1234 x 123'      || '+123.1234x123'
    }

    def "should throw FormatException if empty phone number"() {

        given: "empty, containing only space chars, and/or '+' phone number"
            phoneNumberString = new PhoneNumberString(phoneNumber)

        when: "format for final validation"
            phoneNumberString.formatForFinalValidation()

        then: "throw FormatException"
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

    def "should format phone number for basic validation"() {

        given: "non empty phone number"
            phoneNumberString = new PhoneNumberString(phoneNumber)

        when: "format for basic validation"
            def formattedPhoneNumber = phoneNumberString.formatForBasicValidation()

        then: "create stripped of space special characters and extension phone number"
            expectedPhoneNumber == formattedPhoneNumber
        where:
            phoneNumber             || expectedPhoneNumber
            '+37112345678'          || '+37112345678'
            '+1(123)4567890'        || '+11234567890'
            '+1-(123)4567890'       || '+11234567890'
            '+1-(123)4567890'       || '+11234567890'
            '+1.123456.7890'        || '+11234567890'
            '+123.12345x12345'      || '+12312345'
            '+123.12345X1234'       || '+12312345'
            '+123.12345xX1234'      || '+12312345'
            '\t'                    || ''
            '+ '                    || '+'
            '(.) + -  '             || '+'
            ''                      || ''
            '.x123'                 || ''
    }
}
