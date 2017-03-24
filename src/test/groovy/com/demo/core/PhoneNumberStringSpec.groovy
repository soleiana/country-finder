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

        then: "throw Validation exception"
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

    def "should format phone number for final validation"() {

        given: "non empty phone number"
            phoneNumberString = new PhoneNumberString(phoneNumber)

        when: "format for final validation"
            def formattedPhoneNumber = phoneNumberString.formatForFinalValidation().toNumber()

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
            def formattedPhoneNumber = phoneNumberString.formatForBasicValidation().toNumber()

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
