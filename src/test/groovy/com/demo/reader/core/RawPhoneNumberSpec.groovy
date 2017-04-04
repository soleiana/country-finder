package com.demo.reader.core

import spock.lang.Specification

class RawPhoneNumberSpec extends Specification {

    static final EMPTY_NUMBER = 'phone number is empty'

    def formattedPhoneNumberFactory = Mock(FormattedPhoneNumberFactory)
    def numberString = Mock(RawNumberString)
    def numberStringWithoutSpaceCharacters = Mock(RawNumberString)
    def numberStringWithCallPrefix = Mock(RawNumberString)

    def formattedNumberString = Mock(FormattedNumberString)

    RawPhoneNumber rawPhoneNumber = new RawPhoneNumber(numberString, formattedPhoneNumberFactory)

    def setup() {
        rawPhoneNumber = new RawPhoneNumber(numberString, formattedPhoneNumberFactory)
    }

    def "should create formatted phone number"() {

        when: "format"
            rawPhoneNumber.format()

        then: "remove space characters"
            1 * numberString.withoutSpaceCharacters() >> numberStringWithoutSpaceCharacters

        then: "add international call prefix"
            1 * numberStringWithoutSpaceCharacters.withInternationalCallPrefix() >> numberStringWithCallPrefix

        then: "build formatted phone number"
            1 * numberStringWithCallPrefix.build() >> formattedNumberString

        and: "create formatted phone number"
            1 * formattedPhoneNumberFactory.of(formattedNumberString)
    }
}
