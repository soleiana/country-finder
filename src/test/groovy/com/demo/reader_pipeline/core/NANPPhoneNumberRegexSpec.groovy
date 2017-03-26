package com.demo.reader_pipeline.core

import spock.lang.Specification

import java.util.regex.Pattern

class NANPPhoneNumberRegexSpec extends Specification {

    static final NANP_REGEX = '^(?:\\+?1[-. ]?)?\\(?([0-9]{3})\\)?[-. ]?(?:[0-9]{3})[-. ]?(?:[0-9]{4})$'
    static final NANP_PHONE_NUMBER = Pattern.compile(NANP_REGEX)
    PhoneNumberRegex phoneNumberRegex

    def "should apply to valid phone number in correct format"() {

        given: "valid phone number in correct format"
            phoneNumberRegex = new PhoneNumberRegex(phoneNumber, NANP_PHONE_NUMBER, "")

        when: "NANP regex is applied"
            def result = phoneNumberRegex.apply()

        then: "result is success"
            result
        where:
            phoneNumber             | _
            '1 1234567890'          | _
            '+11234567890'          | _
            '+1 1234567890'         | _
            '+1 123 456 7890'       | _
            '+1 123456 7890'        | _
            '+1 123 4567890'        | _
            '+1 (123) 456 7890'     | _
            '+1-(123) 456 7890'     | _
            '+1-(123) 4567890'      | _
            '+1-(123)-456-7890'     | _
            '+1.123456.7890'        | _
            '+1.123 456 7890'       | _
            '+1.(123) 456 7890'     | _
            '+1.123.456.7890'       | _
            '+1.123456.7890'        | _
    }

    def "should fail if valid phone number in wrong format"() {

        given: "valid phone number in wrong format"
        phoneNumberRegex = new PhoneNumberRegex(phoneNumber, NANP_PHONE_NUMBER, "")

        when: "NANP regex is applied"
            def result = phoneNumberRegex.apply()

        then: "result is failure"
            !result

        where:
            phoneNumber             | _
            '+  11234567890'        | _
            '+1\t1234567890'        | _
            '+1 123  456 7890'      | _
            '+1  (123) 456 7890'    | _
            '+1 - (123) 456 7890'   | _
            '+1-(123)-456 - 7890'   | _
            '+1 .123456 .7890'      | _
            '++1 123 456 7890'      | _
            '+1 ((123)) 456 7890'   | _
            '+1--123 456 7890'      | _
            '+1..123 456 7890'      | _
            '+1 123 (456) 7890'     | _
            '+1 123 ( 456 ) 7890'   | _
            '+1 123 . 456 . 7890'   | _
    }

    def "should fail if invalid phone number"() {

        given: "invalid phone number"
            phoneNumberRegex = new PhoneNumberRegex(phoneNumber, NANP_PHONE_NUMBER, "")

        when: "NANP regex is applied"
            def result = phoneNumberRegex.apply()

        then: "result is failure"
            !result

        where:
            phoneNumber           | _
            '+ 123 4567A901'      | _
            '+1-234-567/8901'     | _
            '+123 456'            | _
            '+1.(34) 56-7'        | _
            '+'                   | _
            ''                    | _
            ' '                   | _
    }
}
