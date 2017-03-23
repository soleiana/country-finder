package com.demo.core

import org.springframework.core.env.Environment
import spock.lang.Specification

import java.util.regex.Pattern

class NANPValidationRuleSpec extends Specification {

    def environment = Stub(Environment)
    def regexFactory = Stub(PhoneNumberRegexFactory)
    def validationRule = new NANPValidationRule(environment, regexFactory)
    def regex = Stub(PhoneNumberRegex)

    def "should apply to correct phone number"() {

        given: "regex passes"
            final phoneNumber = '+1-234-567-8901'
            regex.apply() >> true
            regexFactory.of(_ as String, _ as Pattern, _ as String) >> regex

        when: "rule is applied"
            def result =  validationRule.apply(phoneNumber)

        then: "result is success"
            result
    }

    def "should fail if regex fails"() {

        given: "regex fails"
            final invalidPhoneNumber = '+1A'
            regex.apply() >> false
            regexFactory.of(_ as String, _ as Pattern, _ as String) >> regex

        when: "rule is applied"
             def result = validationRule.apply(invalidPhoneNumber)

        then: "result is failure"
            !result
    }
}
