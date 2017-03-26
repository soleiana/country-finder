package com.demo.reader_pipeline.core

import spock.lang.Specification

import java.util.regex.Pattern

class EPPValidationRuleSpec extends Specification {

    def regexFactory = Stub(PhoneNumberRegexFactory)
    def validationRule = new NANPValidationRule(regexFactory)
    def regex = Stub(PhoneNumberRegex)

    def "should apply to correct phone number"() {

        given: "regex passes"
            final phoneNumber = '+123.4567x1234A'
            regex.apply() >> true
            regexFactory.of(_ as String, _ as Pattern, _ as String) >> regex

        when: "rule is applied"
            def result =  validationRule.apply(phoneNumber)

        then: "result is success"
            result
    }

    def "should fail if regex fails"() {

        given: "regex fails"
            final invalidPhoneNumber = '+123 4567'
            regex.apply() >> false
            regexFactory.of(_ as String, _ as Pattern, _ as String) >> regex

        when: "rule is applied"
            def result = validationRule.apply(invalidPhoneNumber)

        then: "result is failure"
            !result
    }
}
