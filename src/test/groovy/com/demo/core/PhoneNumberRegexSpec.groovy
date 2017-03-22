package com.demo.core

import com.demo.exceptions.ValidationException
import spock.lang.Specification

import java.util.regex.Pattern

class PhoneNumberRegexSpec extends Specification {

    static final BASIC_PHONE_NUMBER_PATTERN = Pattern.compile('^[0-9]{7,15}$')
    static final INVALID_BASIC_FORMAT = 'invalid basic format'
    PhoneNumberRegex phoneNumberRegex

    def "should pass basic validation if valid phone number in correct format"() {

        given: "valid phone number without special characters and extension"
            phoneNumberRegex =
                    new PhoneNumberRegex(phoneNumber, BASIC_PHONE_NUMBER_PATTERN, INVALID_BASIC_FORMAT)

        when: "basic validation regex is applied"
            def result = phoneNumberRegex.apply()

        then: "result is success"
            result
        where:
            phoneNumber         | _
            '12345678901'       | _
            '1234567'           | _
            '123456789012345'   | _
            '0000000'           | _
            '9999999'           | _
    }

    def "should fail basic validation if valid phone number in wrong format"() {

        given: "valid phone number in wrong format"
            phoneNumberRegex =
                    new PhoneNumberRegex(phoneNumber, BASIC_PHONE_NUMBER_PATTERN, INVALID_BASIC_FORMAT)

        when: "basic validation regex is applied"
            phoneNumberRegex.apply()

        then: "throw ValidationException"
            def exception = thrown(ValidationException)
            exception.message == INVALID_BASIC_FORMAT

        where:
            phoneNumber                 | _
            '+1-234-567-8901'           | _
            '1-234-567-8901x1234'       | _
            '+1 (234) 567-8901'         | _
            '1.234.567.8901'            | _
            '+123 4567'                 | _
    }

    def "should fail basic validation if invalid phone number"() {

        given: "invalid phone number"
            phoneNumberRegex =
                    new PhoneNumberRegex(phoneNumber, BASIC_PHONE_NUMBER_PATTERN, INVALID_BASIC_FORMAT)

        when: "basic validation regex is applied"
            phoneNumberRegex.apply()

        then: "throw ValidationException"
            def exception = thrown(ValidationException)
            exception.message == INVALID_BASIC_FORMAT

        where:
            phoneNumber                 | _
            '+ 123 4567A901'            | _
            '+1-234-567/8901'           | _
            '+123 456'                  | _
            '+123 (456) 789-012-3456'   | _
    }
}
