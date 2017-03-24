package com.demo.core

import com.demo.exceptions.FormatException
import spock.lang.Specification

class RawPhoneNumberSpec extends Specification {

    static final EMPTY_NUMBER_MESSAGE = 'phone number is empty'

    def formattedPhoneNumberFactory = Mock(FormattedPhoneNumberFactory)
    def numberString = Mock(PhoneNumberString)
    def formattedNumberString = Mock(PhoneNumberString)

    RawPhoneNumber rawPhoneNumber = new RawPhoneNumber(numberString, formattedPhoneNumberFactory)

    def setup() {
        rawPhoneNumber = new RawPhoneNumber(numberString, formattedPhoneNumberFactory)
    }

    def "should create formatted phone number"() {

        when: "format"
            rawPhoneNumber.format()

        then: "format phone number for final validation"
            1 * numberString.formatForFinalValidation() >> formattedNumberString

        then: "create formatted phone number"
            1 * formattedPhoneNumberFactory.of(formattedNumberString)
    }

    def "should throw FormatException if empty phone number"() {

        when: "format"
            rawPhoneNumber.format()

        then: "throw FormatException"
            1 * numberString.formatForFinalValidation() >> {
                throw new FormatException(EMPTY_NUMBER_MESSAGE)
            }

        then: "FormatException thrown"
            def exception = thrown(FormatException)
            exception.message == EMPTY_NUMBER_MESSAGE
    }

}
