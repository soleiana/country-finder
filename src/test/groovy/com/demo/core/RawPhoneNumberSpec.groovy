package com.demo.core

import com.demo.exceptions.FormatException
import spock.lang.Specification

class RawPhoneNumberSpec extends Specification {

    static final EMPTY_NUMBER_MESSAGE = 'phone number is empty'
    static final RAW_PHONE_NUMBER = '  371 26  39  48   06'
    static final FORMATTED_PHONE_NUMBER = '+37126394806'
    static final EMPTY_PHONE_NUMBER = '+  '

    def formattedPhoneNumberFactory = Mock(FormattedPhoneNumberFactory)
    RawPhoneNumber rawPhoneNumber
    PhoneNumberString numberString

    def "should create formatted phone number"() {

        given: "non empty raw phone number"
            numberString = new PhoneNumberString(RAW_PHONE_NUMBER)
            rawPhoneNumber = createRawPhoneNumber()

        when: "format"
            rawPhoneNumber.format()

        then: "create formatted phone number"
            1 * formattedPhoneNumberFactory.of(FORMATTED_PHONE_NUMBER)
    }

    def "should throw FormatException if empty phone number"() {

        given: "empty, containing only space chars, and/or '+' raw phone number"
            numberString = new PhoneNumberString(EMPTY_PHONE_NUMBER)
            rawPhoneNumber = createRawPhoneNumber()

        when: "format"
            rawPhoneNumber.format()

        then: "throw FormatException"
            def exception = thrown(FormatException)
            exception.message == EMPTY_NUMBER_MESSAGE
    }

    def createRawPhoneNumber() {
        new RawPhoneNumber(numberString, formattedPhoneNumberFactory)
    }
}
