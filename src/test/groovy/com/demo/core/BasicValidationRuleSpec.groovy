package com.demo.core

import com.demo.exceptions.ValidationException
import org.springframework.core.env.Environment
import spock.lang.Specification

import java.util.regex.Pattern

class BasicValidationRuleSpec extends Specification {

    static final INVALID_ITU_T_FORMAT = 'invalid ITU-T format'

    def environment = Stub(Environment)
    def regexFactory = Stub(PhoneNumberRegexFactory)
    def validationRule = new BasicValidationRule(environment, regexFactory)
    def regex = Stub(PhoneNumberRegex)

    def "should apply to correct phone number"() {

        given: "regex passes"
            final formattedPhoneNumber = '37112345678'
            regex.applyWithException() >> true
            regexFactory.of(_ as String, _ as Pattern, _ as String) >> regex

        when: "rule is applied"
           def result =  validationRule.apply(formattedPhoneNumber)

        then: "result is success"
            result
    }

    def "should throw ValidationException if regex fails"() {

        given: "regex throws ValidationException"
            final formattedPhoneNumber = '12345'
            regex.applyWithException() >> {throw new ValidationException(INVALID_ITU_T_FORMAT)}
            regexFactory.of(_ as String, _ as Pattern, _ as String) >> regex

        when: "rule is applied"
            validationRule.apply(formattedPhoneNumber)

        then: "throw ValidationException"
            def exception = thrown(ValidationException)
            exception.message == INVALID_ITU_T_FORMAT
    }
}
