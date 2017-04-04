package com.demo.writer.core

import com.demo.common.Country
import com.demo.common.CountryCode
import spock.lang.Specification


class TokenizedCountriesSpec extends Specification {

    def countryTokens = Mock(CountryTokens)
    def countryParser = Mock(CountryParser)
    def parsedCountryFactory = Mock(ParsedCountryFactory)

    TokenizedCountries tokenizedCountries =
            new TokenizedCountries(countryTokens, countryParser, parsedCountryFactory)

    def "should create parsed countries"() {

        given: "valid tokenized countries"
            def parsedCountryMap = buildParsedCountryMap()

        when: "parse tokenized countries"
            tokenizedCountries.parse()

        then: "create parsed country map"
            1 * countryTokens.apply(_ as CountryParser) >> parsedCountryMap

        then: "create parsed countries"
            1 * parsedCountryFactory.of(_ as ParsedCountryMap)
    }

    def buildParsedCountryMap() {
        Map tuples = new HashMap()
        tuples.put(new CountryCode('7840'), new Country('Abkhazia'))
        tuples.put(new CountryCode('7940'), new Country('Abkhazia'))
        tuples.put(new CountryCode('1'), new Country('United States'))
        tuples
    }
}
