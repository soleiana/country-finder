package com.demo.core

import com.demo.exceptions.ValidationException
import org.springframework.core.env.Environment
import spock.lang.Specification

import java.util.regex.Pattern

class BasicValidationRuleSpec extends Specification {

    static final INVALID_BASIC_FORMAT = 'invalid basic format'
    static final MIN_DIGITS = 7
    static final MAX_DIGITS = 15
    static final EMPTY_NUMBER_MESSAGE = 'phone number is empty'
    static final char EXTENSION_DELIMITER = 'x'

    def environment = Stub(Environment)
    def regexFactory = Stub(PhoneNumberRegexFactory)
    def validationRule = new BasicValidationRule(environment, regexFactory)

    def setup() {
        environment.getRequiredProperty(_ as String, Integer.class) >> MIN_DIGITS
        environment.getRequiredProperty(_ as String, Integer.class) >> MAX_DIGITS
        environment.getRequiredProperty(_ as String) >> EMPTY_NUMBER_MESSAGE
        environment.getRequiredProperty(_ as String, Character.class) >> EXTENSION_DELIMITER
        validationRule.initialize()
    }
    def "should be successfully applied to correct phone number"() {

        given: "regex passes"
            def regex = Stub(PhoneNumberRegex)
            regex.apply() >> true
            regexFactory.of(_ as String, _ as Pattern, _ as String) >> regex

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

    def "should throw ValidationException if regex fails"() {

        given: "regex throws ValidationException"
            def regex = Stub(PhoneNumberRegex)
            regex.apply() >> {throw new ValidationException(INVALID_BASIC_FORMAT)}
            regexFactory.of(_ as String, _ as Pattern, _ as String) >> regex
            final invalidPhoneNumber = '+ 123 4567A901'

        when: "rule is applied"
            validationRule.apply(invalidPhoneNumber)

        then: "throw ValidationException"
            def exception = thrown(ValidationException)
            exception.message == INVALID_BASIC_FORMAT
    }
}
