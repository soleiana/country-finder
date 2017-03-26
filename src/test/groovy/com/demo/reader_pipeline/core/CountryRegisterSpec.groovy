package com.demo.reader_pipeline.core

import com.demo.reader_pipeline.exceptions.SearchException
import spock.lang.Specification


class CountryRegisterSpec extends Specification {

    static final VALID_LATVIAN_PHONE_NUMBER = '37112345678'
    static final LATVIAN_CODE = '371'
    static final PHONE_NUMBER_WITH_UNASSIGNED_CODE = '29212345678'

    static final COUNTRY_CODE_NOT_FOUND = 'country code not found'
    static final COUNTRY_NOT_FOUND = 'country not found'

    def countryStorage = Mock(CountryStorage)
    def countryRegister = new CountryRegister(countryStorage)

    def "should find country in storage by phone" () {

        given: "valid Latvian phone number"
            def latvia = Optional.of(new Country('Latvia'))

        when: "search country"
            def result = countryRegister.searchCountry(VALID_LATVIAN_PHONE_NUMBER)

        then: "find all country codes"
            1 * countryStorage.findAllCountryCodes() >> [
                    new CountryCode('372'),
                    new CountryCode(LATVIAN_CODE),
                    new CountryCode('373')
            ].toSet()

        then: "find country by code"
            1 * countryStorage.findCountryByCode(_ as CountryCode) >> latvia

        and: "Latvia found"
            result == latvia.get()
    }

    def "should throw SearchException if country code not found" () {

        given: "valid phone number with unassigned code (292)"

        when: "search country"
            countryRegister.searchCountry(PHONE_NUMBER_WITH_UNASSIGNED_CODE)

        then: "find all country codes"
        1 * countryStorage.findAllCountryCodes() >> [
                new CountryCode('230'),
                new CountryCode('231'),
                new CountryCode('232')
        ].toSet()

        then: "find country by code not invoked"
         0 * countryStorage.findCountryByCode(_ as CountryCode)

        and: "SearchException thrown"
            def exception = thrown(SearchException)
            exception.message == COUNTRY_CODE_NOT_FOUND
    }

    def "should throw SearchException if country not found" () {

        given: "valid Latvian phone number"

        when: "search country"
            countryRegister.searchCountry(VALID_LATVIAN_PHONE_NUMBER)

        then: "find all country codes"
            1 * countryStorage.findAllCountryCodes() >> [
                new CountryCode('370'),
                new CountryCode(LATVIAN_CODE),
                new CountryCode('372')
            ].toSet()

        then: "country not found"
            1 * countryStorage.findCountryByCode(_ as CountryCode) >> Optional.empty()

        and: "SearchException thrown"
            def exception = thrown(SearchException)
            exception.message == COUNTRY_NOT_FOUND
    }
}
