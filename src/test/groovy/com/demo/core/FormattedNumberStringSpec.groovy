package com.demo.core

import spock.lang.Specification

class FormattedNumberStringSpec extends Specification {

    static final PHONE_NUMBER = '+371 12345678'
    static final INVALID_ITU_T_PHONE_NUMBER = '+371 12345(678)'

    FinalValidationRule validationRule = Mock(FinalValidationRule)
    FormattedNumberString phoneNumberString

    def "should apply validation rule"() {

        given: "valid phone number"
            phoneNumberString = createFormattedNumberString(PHONE_NUMBER)

        when: "apply rule"
            def result = phoneNumberString.apply(validationRule)

        then: "delegate validation"
            1 * validationRule.apply(PHONE_NUMBER) >> true

        and: "result is success"
            result
    }

    def "should fail if validation rule fails"() {

        given: "invalid phone number"
            phoneNumberString = createFormattedNumberString(INVALID_ITU_T_PHONE_NUMBER)

        when: "apply rule"
            def result = phoneNumberString.apply(validationRule)

        then: "delegate validation"
            1 * validationRule.apply(INVALID_ITU_T_PHONE_NUMBER) >> false

        and: "result is failure"
             !result
    }

    def "should remove special characters from phone number"() {

        given: "non empty phone number"
            phoneNumberString = createFormattedNumberString(phoneNumber)

        when: "remove special characters: '.', '(', ')', '\\s', '-'"
            def formattedPhoneNumber = phoneNumberString.withoutSpecialCharacters()

        then: "create phone number without special characters"
            formattedPhoneNumber == createFormattedNumberString(expectedPhoneNumber)
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
            phoneNumberString = createFormattedNumberString(phoneNumber)

        when: "remove extension"
            def formattedPhoneNumber = phoneNumberString.withoutExtension()

        then: "create phone number without extension"
            formattedPhoneNumber == createFormattedNumberString(expectedPhoneNumber)
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

    def createFormattedNumberString(String phoneNumber) {
        new FormattedNumberString(phoneNumber)
    }

}
