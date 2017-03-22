package com.demo.core

import com.demo.exceptions.ValidationException
import org.springframework.core.env.Environment
import spock.lang.Specification

class BasicValidationRuleSpec extends Specification {

    static final MIN_DIGITS_IN_USE_PROPERTY = 'phone.number.min_digits_in_use'
    static final MAX_DIGITS_ITU_T_PROPERTY = 'phone.number.max_digits_itu_t'
    static final EMPTY_NUMBER_MESSAGE_PROPERTY = 'phone.number.empty_number_message'
    static final INVALID_BASIC_FORMAT = 'invalid basic format'
    static final MIN_DIGITS = 7
    static final MAX_DIGITS = 15
    static final EMPTY_NUMBER_MESSAGE = 'phone number is empty'

    def environment = Stub(Environment)
    def validationRule = new BasicValidationRule(environment)

    def setup() {
        environment.getProperty(MIN_DIGITS_IN_USE_PROPERTY, Integer.class) >> MIN_DIGITS
        environment.getProperty(MAX_DIGITS_ITU_T_PROPERTY, Integer.class) >> MAX_DIGITS
        environment.getProperty(EMPTY_NUMBER_MESSAGE_PROPERTY) >> EMPTY_NUMBER_MESSAGE
        validationRule.initialize()
    }
    def "should be successfully applied to correct phone number"() {

        when: "rule is applied"
           def result =  validationRule.apply(phoneNumber)

        then: "result is success"
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
