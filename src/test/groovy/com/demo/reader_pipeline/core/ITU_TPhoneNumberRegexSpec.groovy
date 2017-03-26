package com.demo.reader_pipeline.core

import com.demo.reader_pipeline.exceptions.ValidationException
import spock.lang.Specification

import java.util.regex.Pattern

class ITU_TPhoneNumberRegexSpec extends Specification {

    static final ITU_T_PHONE_NUMBER_STRICT = Pattern.compile('^\\+[0-9]{7,15}$')
    static final ITU_T_PHONE_NUMBER_LAX = Pattern.compile('^\\+(?:[0-9] ?){6,14}[0-9]$')
    static final INVALID_ITU_T_FORMAT = 'invalid ITU-T format'
    PhoneNumberRegex phoneNumberRegex

    def "should apply strict validation to valid phone number in correct format"() {

        given: "valid phone number without special characters and extension"
            phoneNumberRegex = new PhoneNumberRegex(phoneNumber, ITU_T_PHONE_NUMBER_STRICT, "")

        when: "ITU-T strict regex is applied"
            def result = phoneNumberRegex.applyWithException()

        then: "result is success"
            result
        where:
            phoneNumber          | _
            '+12345678901'       | _
            '+1234567'           | _
            '+123456789012345'   | _
    }

    def "should fail strict validation if valid phone number in wrong format"() {

        given: "valid phone number in wrong format"
            phoneNumberRegex = new PhoneNumberRegex(phoneNumber, ITU_T_PHONE_NUMBER_STRICT, INVALID_ITU_T_FORMAT)

        when: "ITU-T strict regex is applied"
            phoneNumberRegex.applyWithException()

        then: "ValidationException thrown"
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

    def "should fail strict validation if invalid phone number"() {

        given: "invalid phone number"
            phoneNumberRegex = new PhoneNumberRegex(phoneNumber, ITU_T_PHONE_NUMBER_STRICT, INVALID_ITU_T_FORMAT)

        when: "ITU-T strict regex is applied"
            phoneNumberRegex.applyWithException()

        then: "ValidationException thrown"
            def exception = thrown(ValidationException)
            exception.message == INVALID_ITU_T_FORMAT

        where:
            phoneNumber                 | _
            '+ 123 4567A901'            | _
            '+1-234-567/8901'           | _
            '+123456'                   | _
            '+1234567890123456'         | _
    }

    def "should apply lax validation to valid phone number in correct format"() {

        given: "valid phone number without special characters and extension"
            phoneNumberRegex = new PhoneNumberRegex(phoneNumber, ITU_T_PHONE_NUMBER_LAX, "")

        when: "ITU-T lax regex is applied"
            def result = phoneNumberRegex.apply()

        then: "result is success"
            result
        where:
            phoneNumber             | _
            '+12345678901'          |_
            '+1 2345678901'         | _
            '+123 123 456 78'       | _
            '+123 456 789 012 345'  | _

    }

    def "should fail lax validation if valid phone number in wrong format"() {

        given: "valid phone number in wrong format"
            phoneNumberRegex = new PhoneNumberRegex(phoneNumber, ITU_T_PHONE_NUMBER_LAX, INVALID_ITU_T_FORMAT)

        when: "ITU-T lax regex is applied"
            def result = phoneNumberRegex.apply()

        then: "result is failure"
            !result
        where:
            phoneNumber                 | _
            '+123  123 45678'           | _
            '12312345678'               | _
            '+1 (123) 456 7890'         | _
            '+123.1234x123'             | _
    }

    def "should fail lax validation if invalid phone number"() {

        given: "invalid phone number"
            phoneNumberRegex = new PhoneNumberRegex(phoneNumber, ITU_T_PHONE_NUMBER_LAX, INVALID_ITU_T_FORMAT)

        when: "ITU-T lax regex is applied"
            def result = phoneNumberRegex.apply()

        then: "result is failure"
            !result
        where:
            phoneNumber                 | _
            '+123123456789012345'       | _
            '+123456'                   | _
            '-1234567'                  | _
            '+1234a1234'                | _
            '+'                         | _
            ''                          | _
            ' '                         | _
            '  '                        | _
    }
}
