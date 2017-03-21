package com.demo.core

import com.demo.exceptions.ValidationException
import spock.lang.Specification

class PhoneNumberBasicValidationRuleSpec extends Specification {

    final INVALID_BASIC_FORMAT = "invalid basic format"

    def validationRule = new PhoneNumberBasicValidationRule()

    def "should be successfully applied to correct phone number"() {

        given: "rule is applied"
           def result =  validationRule.apply(phoneNumber)

        expect: "successful result"
            result
        where:
            phoneNumber                 | _
            '+ 123 45678901'            | _
            '+1-234-567-8901'           | _
            '1-234-567-8901'            | _
            '1-234-567-8901x1234'       | _
            '1 (234) 567-8901'          | _
            '+1 (234) 567-8901'         | _
            '1.234.567.8901'            | _
            '12345678901'               | _
            '+123 4567'                 | _
            '+123 (456) 789-012-345'    | _
    }

    def "should throw ValidationException if phone number does not match basic format"() {

        when: "rule is applied"
            validationRule.apply(phoneNumber)

        then: "expect ValidationException"
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
