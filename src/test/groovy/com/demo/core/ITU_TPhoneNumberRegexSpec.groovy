package com.demo.core

import com.demo.exceptions.ValidationException
import spock.lang.Specification

import java.util.regex.Pattern

class ITU_TPhoneNumberRegexSpec extends Specification {

    static final ITU_T_PHONE_NUMBER_WITHOUT_CHARACTERS = Pattern.compile('^\\+[0-9]{7,15}$')
    static final INVALID_ITU_T_FORMAT = 'invalid ITU-T format'
    PhoneNumberRegex phoneNumberRegex

    def "should apply to valid phone number in correct format"() {

        given: "valid phone number without special characters and extension"
            phoneNumberRegex =
                    new PhoneNumberRegex(phoneNumber, ITU_T_PHONE_NUMBER_WITHOUT_CHARACTERS, INVALID_ITU_T_FORMAT)

        when: "ITU-T regex is applied"
            def result = phoneNumberRegex.applyWithException()

        then: "result is success"
            result
        where:
            phoneNumber          | _
            '+12345678901'       | _
            '+1234567'           | _
            '+123456789012345'   | _
            '+0000000'           | _
            '+9999999'           | _
    }

    def "should fail if valid phone number in wrong format"() {

        given: "valid phone number in wrong format"
            phoneNumberRegex =
                    new PhoneNumberRegex(phoneNumber, ITU_T_PHONE_NUMBER_WITHOUT_CHARACTERS, INVALID_ITU_T_FORMAT)

        when: "ITU-T regex is applied"
            phoneNumberRegex.applyWithException()

        then: "throw ValidationException"
            def exception = thrown(ValidationException)
            exception.message == INVALID_ITU_T_FORMAT

        where:
            phoneNumber                 | _
            '1 234 567 8901'            | _
            '+1-234-567-8901'           | _
            '1-234-567-8901x1234'       | _
            '+1 (234) 567-8901'         | _
            '1.234.567.8901'            | _
            '+123 4567'                 | _
    }

    def "should fail if invalid phone number"() {

        given: "invalid phone number"
            phoneNumberRegex =
                    new PhoneNumberRegex(phoneNumber, ITU_T_PHONE_NUMBER_WITHOUT_CHARACTERS, INVALID_ITU_T_FORMAT)

        when: "ITU-T regex is applied"
            phoneNumberRegex.applyWithException()

        then: "throw ValidationException"
            def exception = thrown(ValidationException)
            exception.message == INVALID_ITU_T_FORMAT

        where:
            phoneNumber                 | _
            '+ 123 4567A901'            | _
            '+1-234-567/8901'           | _
            '+123456'                   | _
            '+1234567890123456'         | _
    }
}
