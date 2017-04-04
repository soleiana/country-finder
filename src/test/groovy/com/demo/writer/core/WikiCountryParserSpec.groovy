package com.demo.writer.core

import spock.lang.Specification


class WikiCountryParserSpec extends Specification {

    def countryParser = new WikiCountryParser()

    def "should apply to valid country token"() {

        given: "valid country token"
            def result = countryParser.apply(token)

        expect: "parsed country name"
            result == expectedResult

        where:
            token                                                                   | expectedResult
            "[[Area code 441|+1 441]] – {{flag|Bermuda}}"                           | 'Bermuda'
            "[[Area code 649|+1 649]] – {{flag|Turks and Caicos Islands}}"          | 'Turks and Caicos Islands'
            "[[North American Numbering Plan|+1]] – {{flag|Canada}}"                | 'Canada'
            "[[+212]] – {{flag|Morocco}}"                                           | 'Morocco'
            "[[Telephone numbers in Kazakhstan|+7 3xx / 7xx]]– {{flag|Kazakhstan}}" | 'Kazakhstan'
            "[[Telephone numbers in Abkhazia|+7 840 / 940]] – {{flag|Abkhazia}}"    | 'Abkhazia'
    }
}
