package com.demo.core

import com.demo.exceptions.FormatException
import spock.lang.Specification

class RawPhoneNumberSpec extends Specification {

    static final EMPTY_NUMBER_MESSAGE = 'phone number is empty'
    static final RAW_PHONE_NUMBER = '  371 26  39  48   06'
    static final FORMATTED_PHONE_NUMBER = '+37126394806'
    static final EMPTY_PHONE_NUMBER = '+  '

    def formattedPhoneNumberFactory = Mock(FormattedPhoneNumberFactory)
    def formattingOperations = Mock(FormattingOperations)
    RawPhoneNumber rawPhoneNumber

    def "should create formatted phone number"() {

        given: "non empty raw phone number"
            rawPhoneNumber =
                    new RawPhoneNumber(RAW_PHONE_NUMBER, formattedPhoneNumberFactory, formattingOperations)
        when: "format"
            rawPhoneNumber.format()

        then: "apply formatting operations"
            1 * formattingOperations.apply(RAW_PHONE_NUMBER) >> FORMATTED_PHONE_NUMBER

        and: "create formatted phone number"
            1 * formattedPhoneNumberFactory.of(FORMATTED_PHONE_NUMBER)
    }

    def "should throw FormatException if empty phone number"() {

        given: "empty, containing only space chars, and/or '+' raw phone number"
            rawPhoneNumber =
                    new RawPhoneNumber(EMPTY_PHONE_NUMBER, formattedPhoneNumberFactory, formattingOperations)
        when: "format"
            rawPhoneNumber.format()

        then: "throw FormatException"
            thrown(FormatException)
            1 * formattingOperations.apply(EMPTY_PHONE_NUMBER) >> {
                throw new FormatException(EMPTY_NUMBER_MESSAGE)
            }

    }
}
