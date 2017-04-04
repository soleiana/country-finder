package com.demo.common

import spock.lang.Specification

class CountryStorageSpec extends Specification {

    CountryStorage countryStorage = new CountryStorage()

    def setup() {
        countryStorage.init()
    }

    def "should find all country codes"() {

        given:
            def expectedResult = [new CountryCode('071'), new CountryCode('070')] as Set

        when: "find all country codes"
            def result = countryStorage.findAllCountryCodes()

        then: "all the codes found"
            result == expectedResult
    }

    def "should find country by phone number"() {

        when: "find country by code"
            def result = countryStorage.findCountryByCode(code)

        then: "all the codes found"
            result == country
        where:
            code                     ||  country
            new CountryCode('071')   || Optional.of(new Country('Serendipity'))
            new CountryCode('070')   || Optional.of(new Country('Oz'))
            new CountryCode('072')   || Optional.empty()
    }

    def "should save countries"() {

        given: "parsed country map"
            def map = buildMap()
            def expectedResult = new Country('Disney Land')

        when: "save countries"
            countryStorage.save(map)

        then: "find country by code"
            def result = countryStorage.findCountryByCode(new CountryCode('075'))

        and: "country found"
            result.get() == expectedResult
    }

    def buildMap() {
        Map<CountryCode, Country> map = new HashMap<>()
        map.put(new CountryCode('075'), new Country('Disney Land'))
        map
    }
}
