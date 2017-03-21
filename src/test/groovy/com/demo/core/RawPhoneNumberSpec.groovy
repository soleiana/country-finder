package com.demo.core

import com.demo.exceptions.FormatException
import spock.lang.Specification

class RawPhoneNumberSpec extends Specification {

    final EMPTY_NUMBER_MESSAGE = "phone number is empty"
    final RAW_PHONE_NUMBER = '  371 26  39  48   06'
    final FORMATTED_PHONE_NUMBER = '+371 26 39 48 06'
    final EMPTY_PHONE_NUMBER = '+  '

    def formattedPhoneNumberFactory = Mock(FormattedPhoneNumberFactory)
    def phoneNumberFormatter = Mock(PhoneNumberFormatter)
    RawPhoneNumber rawPhoneNumber

    def "should return formatted phone number"() {

        given: "non empty phone number"
            rawPhoneNumber =
                    new RawPhoneNumber(RAW_PHONE_NUMBER, formattedPhoneNumberFactory, phoneNumberFormatter)

        when:
            rawPhoneNumber.format()
        then:
            1 * phoneNumberFormatter.apply(RAW_PHONE_NUMBER) >> FORMATTED_PHONE_NUMBER
            1 * formattedPhoneNumberFactory.of(FORMATTED_PHONE_NUMBER)
    }

    def "should throw FormatException if empty phone number"() {

        given: "empty, containing only space chars, and/or '+' phone number"
            rawPhoneNumber =
                    new RawPhoneNumber(EMPTY_PHONE_NUMBER, formattedPhoneNumberFactory, phoneNumberFormatter)
        when:
            rawPhoneNumber.format()

        then:
            thrown(FormatException)
            1 * phoneNumberFormatter.apply(EMPTY_PHONE_NUMBER) >> {throw new FormatException(EMPTY_NUMBER_MESSAGE)}



    }
}
