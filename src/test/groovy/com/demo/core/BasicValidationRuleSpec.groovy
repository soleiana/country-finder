package com.demo.core

import com.demo.exceptions.ValidationException
import org.springframework.core.env.Environment
import spock.lang.Specification

import java.util.regex.Pattern

class BasicValidationRuleSpec extends Specification {

    static final INVALID_ITU_T_FORMAT = 'invalid ITU-T format'
    static final EMPTY_NUMBER_MESSAGE = 'phone number is empty'
    static final char EXTENSION_DELIMITER = 'x'

    def environment = Stub(Environment)
    def regexFactory = Stub(PhoneNumberRegexFactory)
    def validationRule = new BasicValidationRule(environment, regexFactory)
    def regex = Stub(PhoneNumberRegex)

    def setup() {
        environment.getRequiredProperty(_ as String) >> EMPTY_NUMBER_MESSAGE
        environment.getRequiredProperty(_ as String, Character.class) >> EXTENSION_DELIMITER
        validationRule.initialize()
    }
    def "should apply to correct phone number"() {

        given: "regex passes"
            regex.applyWithException() >> true
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
            final invalidPhoneNumber = '+1A'
            regex.applyWithException() >> {throw new ValidationException(INVALID_ITU_T_FORMAT)}
            regexFactory.of(_ as String, _ as Pattern, _ as String) >> regex

        when: "rule is applied"
            validationRule.apply(invalidPhoneNumber)

        then: "throw ValidationException"
            def exception = thrown(ValidationException)
            exception.message == INVALID_ITU_T_FORMAT
    }
}
