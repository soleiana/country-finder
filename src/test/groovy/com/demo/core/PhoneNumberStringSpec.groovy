package com.demo.core

import com.demo.exceptions.FormatException
import com.demo.exceptions.ValidationException
import spock.lang.Specification

class PhoneNumberStringSpec extends Specification {

    static final INVALID_ITU_T_FORMAT = 'invalid ITU-T format'
    static final EMPTY_NUMBER_MESSAGE = 'phone number is empty'
    static final PHONE_NUMBER = '+371 12345678'
    static final INVALID_PHONE_NUMBER = '+371 1234567A'
    static final INVALID_ITU_T_PHONE_NUMBER = '+371 12345(678)'

    PhoneNumberValidationRule validationRule
    PhoneNumberString phoneNumberString

    def "should apply validation rule"() {

        given: "basic validation rule is provided"
            phoneNumberString = new PhoneNumberString(PHONE_NUMBER)
            validationRule = Mock(BasicValidationRule)

        when: "apply rule"
            def result = phoneNumberString.apply(validationRule)

        then: "pass rule"
            1 * validationRule.apply(PHONE_NUMBER) >> true

        and: "result is success"
            result
    }

    def "should throw exception if validation rule throws exception"() {

        given: "basic validation rule is provided"
            phoneNumberString = new PhoneNumberString(INVALID_PHONE_NUMBER)
            validationRule = Mock(BasicValidationRule)

        when: "apply rule"
            phoneNumberString.apply(validationRule)

        then: "invoke rule and throw ValidationException"
            1 * validationRule.apply(INVALID_PHONE_NUMBER) >> {
                throw new ValidationException(INVALID_ITU_T_FORMAT)
            }

        then: "ValidationException thrown"
            def exception = thrown(ValidationException)
            exception.message == INVALID_ITU_T_FORMAT
    }

    def "should fail if validation rule fails"() {

        given: "ITU-T validation rule is provided"
            phoneNumberString = new PhoneNumberString(INVALID_ITU_T_PHONE_NUMBER)
            validationRule = Mock(BasicValidationRule)

        when: "apply rule"
            def result = phoneNumberString.apply(validationRule)

        then: "fail rule"
             1 * validationRule.apply(INVALID_ITU_T_PHONE_NUMBER) >> false

        and: "result is failure"
            !result
    }

    def "should remove white space characters from phone number"() {

        given: "non empty phone number"
            phoneNumberString = new PhoneNumberString(phoneNumber)

        when: "remove space characters"
            def formattedPhoneNumber = phoneNumberString
                    .withoutSpaceCharacters()
                    .toNumber()

        then: "create phone number without space characters"
            expectedPhoneNumber == formattedPhoneNumber
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
             phoneNumberString = new PhoneNumberString(phoneNumber)

        when: "add call prefix"
            def formattedPhoneNumber = phoneNumberString
                .withInternationalCallPrefix()
                .toNumber()

        then: "create phone number with prefix"
            expectedPhoneNumber == formattedPhoneNumber
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

    def "should throw FormatException if empty phone number"() {

        given: "empty, containing only space chars, and/or '+' phone number"
            phoneNumberString = new PhoneNumberString(phoneNumber)

        when: "format for final validation"
            phoneNumberString.checkFormat()

        then: "FormatException thrown"
            def exception = thrown(FormatException)
            exception.message == EMPTY_NUMBER_MESSAGE

        where:
            phoneNumber     | _
            ''              | _
            '+'             | _
            'a'             | _
    }

    def "should remove special characters from phone number"() {

        given: "non empty phone number"
            phoneNumberString = new PhoneNumberString(phoneNumber)

        when: "remove special characters: '.', '(', ')', '\\s', '-'"
            def formattedPhoneNumber = phoneNumberString
                    .withoutSpecialCharacters()
                    .toNumber()

        then: "create phone number without special characters"
            expectedPhoneNumber == formattedPhoneNumber
        where:
            phoneNumber             || expectedPhoneNumber
            '+37112345678'          || '+37112345678'
            '+1(123)4567890'        || '+11234567890'
            '+1-(123)4567890'       || '+11234567890'
            '+1-(123)4567890'       || '+11234567890'
            '+1.123456.7890'        || '+11234567890'
            '+123.12345x12345'      || '+12312345x12345'
            '\t'                    || ''
            '+ '                    || '+'
            '(.) + -  '             || '+'
            ''                      || ''
            '.x123'                 || 'x123'
    }

    def "should remove extension from phone number"() {

        given: "non empty phone number"
            phoneNumberString = new PhoneNumberString(phoneNumber)

        when: "remove extension"
            def formattedPhoneNumber = phoneNumberString
                .withoutExtension()
                .toNumber()

        then: "create phone number without extension"
            expectedPhoneNumber == formattedPhoneNumber
        where:
            phoneNumber             || expectedPhoneNumber
            '+12312345x12345'       || '+12312345'
            '+123.1234x12345'       || '+123.1234'
            '  123.1234x12345'      || '  123.1234'
            '123.1234X12345'        || '123.1234'
            '123.1234x123x5'        || '123.1234'
            '+123.1234'             || '+123.1234'
            ''                      || ''
            '.x123'                 || '.'
    }
}
