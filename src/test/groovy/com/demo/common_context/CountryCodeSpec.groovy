package com.demo.common_context

import spock.lang.Specification


class CountryCodeSpec extends Specification {

    def "should sort by code length in descending order"() {

        given: "list of country codes"
            def codes = [new CountryCode('1'),
                         new CountryCode('62'),
                         new CountryCode('371'),
                         new CountryCode('11')]

            def expectedCodes = [new CountryCode('371'),
                                 new CountryCode('62'),
                                 new CountryCode('11'),
                                 new CountryCode('1')]


        when: "sort list"
            def result = codes.sort()

        then: "codes sorted by code length, descending"
            result == expectedCodes
    }

    def "should belong to phone number"() {

        given: "country code and phone number"
            def countryCode = new CountryCode(code)
            def result = countryCode.belongsTo(phoneNumber)

        expect:
            result == expectedResult
        where:
            phoneNumber     |  code   || expectedResult
            '37112345678'   | '371'   || true
            '11234567890'   | '1'     || true
            '37212345678'   | '371'   || false

    }
}
