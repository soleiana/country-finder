package com.demo.writer_pipeline.core

import spock.lang.Specification

class ParsedCountriesSpec extends Specification {

    def serializer = Mock(CountrySerializer)
    def parsedCountryMap = Mock(ParsedCountryMap)

    ParsedCountries parsedCountries = new ParsedCountries(parsedCountryMap, serializer)

    def "should serialize parsed countries"() {

        given: "parsed country map"

        when: "serialize"
            parsedCountries.serialize()

        then: "serialize parsed countries"
            1 * parsedCountryMap.apply(_ as CountrySerializer)

    }
}
