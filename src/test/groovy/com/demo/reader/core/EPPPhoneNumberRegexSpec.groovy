package com.demo.reader.core

import spock.lang.Specification

import java.util.regex.Pattern

class EPPPhoneNumberRegexSpec extends Specification {

    static final EPP_REGEX = '^\\+[0-9]{1,3}\\.[0-9]{4,14}(?:[x|X].+)?$'
    static final EPP_PHONE_NUMBER = Pattern.compile(EPP_REGEX)
    PhoneNumberRegex phoneNumberRegex

    def "should apply to valid phone number in correct format"() {

        given: "valid phone number in correct format"
            phoneNumberRegex = new PhoneNumberRegex(phoneNumber, EPP_PHONE_NUMBER, "")

        when: "EPP regex is applied"
            def result = phoneNumberRegex.apply()

        then: "result is success"
            result
        where:
            phoneNumber                 | _
            '+1.1234'                   | _
            '+123.12345678901234'       | _
            '+123.12345x12345'          | _
            '+123.12345X12345'          | _
            '+123.12345xXX'             | _
            '+123.12345xA123'           | _
            '+123.12345x123-abc'        | _
            '+123.12345x123 abc'        | _
    }

    def "should fail if valid phone number in wrong format"() {

        given: "valid phone number in wrong format"
             phoneNumberRegex = new PhoneNumberRegex(phoneNumber, EPP_PHONE_NUMBER, "")

        when: "EPP regex is applied"
            def result = phoneNumberRegex.apply()

        then: "result is failure"
            !result

        where:
             phoneNumber                 | _
            '+ 123.4567'                 | _
            '+123. 12345678901234'       | _
            '+1(123)4567890x12345'       | _
            '+1.123.4567890X12345'       | _
            '+1234567890x12345'          | _
    }

    def "should fail if invalid phone number"() {

        given: "invalid phone number"
            phoneNumberRegex = new PhoneNumberRegex(phoneNumber, EPP_PHONE_NUMBER, "")

        when: "NANP regex is applied"
            def result = phoneNumberRegex.apply()

        then: "result is failure"
            !result

        where:
            phoneNumber        | _
            '+123.12345x'      | _
            '+.x'              | _
            '+123.12345aaa'    | _
            '+'                | _
            ''                 | _
            ' '                | _
    }
}
