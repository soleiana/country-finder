package com.demo.writer.core

import com.demo.common.Country
import com.demo.common.CountryCode
import spock.lang.Specification


class WikiParserSpec extends Specification {

    def wikiTokenParser = Mock(WikiTokenParser)
    def wikiParser = new WikiParser(wikiTokenParser)
    def usaToken = "[[North American Numbering Plan|+1]] – {{flag|United States}}"
    def canadaToken = "[[North American Numbering Plan|+1]] – {{flag|Canada}}"
    def abkhaziaToken = "[[Telephone numbers in Abkhazia|+7 840 / 940]] – {{flag|Abkhazia}}"

    def "should create parsed code-country map"() {

        given: "valid Abkhazia and USA tokens"

            def tokens = [abkhaziaToken, usaToken]

        when: "apply Wiki parser "
            def result = wikiParser.apply(tokens)

        then: "apply Wiki token parser to Abkhazia"
            1 * wikiTokenParser.apply(abkhaziaToken) >> buildAbkhaziaTuples()

        then: "apply Wiki token parser to USA"
            1 * wikiTokenParser.apply(usaToken) >> buildUSATuples()

        and: "created parsed code-country map"
            result == buildParsedCountryMap()
    }

    def "should not put country with a duplicate code to code-country map"() {

        given: "valid USA and Canada tokens"

            def tokens = [usaToken, canadaToken]
            def usaTuples = buildUSATuples()

        when: "apply Wiki parser "
            def result = wikiParser.apply(tokens)

        then: "apply Wiki token parser to USA"
            1 * wikiTokenParser.apply(usaToken) >> usaTuples

        then: "apply Wiki token parser to Canada"
            1 * wikiTokenParser.apply(canadaToken) >> buildCanadaTuples()

        and: "created parsed code-country only with USA"
            result == usaTuples
    }

    def buildUSATuples() {
        Map tuples = new HashMap()
        tuples.put(new CountryCode('1'), new Country('United States'))
        tuples
    }

    def buildCanadaTuples() {
        Map tuples = new HashMap()
        tuples.put(new CountryCode('1'), new Country('Canada'))
        tuples
    }

    def buildAbkhaziaTuples() {
        Map tuples = new HashMap()
        tuples.put(new CountryCode('7840'), new Country('Abkhazia'))
        tuples.put(new CountryCode('7940'), new Country('Abkhazia'))
        tuples
    }

    def buildParsedCountryMap() {
        Map tuples = new HashMap()
        tuples.put(new CountryCode('7840'), new Country('Abkhazia'))
        tuples.put(new CountryCode('7940'), new Country('Abkhazia'))
        tuples.put(new CountryCode('1'), new Country('United States'))
        tuples
    }

}
