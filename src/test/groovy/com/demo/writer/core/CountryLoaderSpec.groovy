package com.demo.writer.core

import com.demo.writer.communications.Countries
import com.demo.writer.communications.RestClientAdapter
import spock.lang.Specification

class CountryLoaderSpec extends Specification {

    def restClientAdapter = Mock(RestClientAdapter)
    def rawCountryFactory = Mock(RawCountryFactory)
    def countries = Mock(Countries)
    def rawCountries =  Mock(RawCountries)
    def tokenizedCountries = Mock(TokenizedCountries)
    def parsedCountries = Mock(ParsedCountries)

    CountryLoader countryLoader = new CountryLoader(restClientAdapter, rawCountryFactory)

    def "should get from web, process and save list of country calling codes"() {

        when: "loader bootstraps"
            countryLoader.bootstrap()

        then: "it gets list of country calling codes from web"
            1 * restClientAdapter.listOfCountryCallingCodes >> countries

        then: "creates initial (raw) string from web text"
            1 * rawCountryFactory.of(countries) >> rawCountries

        then: "splits raw string into processable tokens, filters relevant tokens"
            1 * rawCountries.tokenize() >> tokenizedCountries

        then: "parses tokens into [country code: country] tuples"
            1 * tokenizedCountries.parse() >> parsedCountries

        and: "serializes tuples as key-value pairs to data store"
            1 * parsedCountries.serialize()

    }
}
