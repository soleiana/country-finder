package com.demo.writer.core

import spock.lang.Specification

class RawCountriesSpec extends Specification {

    def rawCountryString = Mock(RawCountryString)
    def tokenizedCountryFactory = Mock(TokenizedCountryFactory)
    def countryTokenizer = Mock(CountryTokenizer)

    def rawCountries = new RawCountries(rawCountryString, countryTokenizer, tokenizedCountryFactory)

    def "should create tokenized countries"() {

        given: "valid raw countries"
            def countyTokens  = new CountryTokens(['[[+353]] – {{flag|Ireland}}', '[[+354]] – {{flag|Iceland}}'])

        when: "tokenize raw countries "
             rawCountries.tokenize()

        then: "create valid tokens"
            1 * rawCountryString.apply(_ as CountryTokenizer) >> countyTokens

        and: "create tokenized countries"
            1 * tokenizedCountryFactory.of(_ as CountryTokens)

    }
}
