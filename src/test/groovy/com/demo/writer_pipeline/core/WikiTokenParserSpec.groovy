package com.demo.writer_pipeline.core

import com.demo.common_context.Country
import com.demo.common_context.CountryCode
import com.demo.writer_pipeline.exceptions.ParsingException
import spock.lang.Specification


class WikiTokenParserSpec extends Specification {

    static final CODE_PARSING_ERROR = "unable to parse country code"

    def codeParser = Mock(WikiCodeParser)
    def countryParser = Mock(WikiCountryParser)

    def tokenParser = new WikiTokenParser(codeParser, countryParser)

    def "should create code-country tuples"() {

        given: "valid country token"
            def parsedCountry = 'Kazakhstan'
            def parsedCodes = ['73', '77']
            def expectedResult = buildCodeCountryTuples(parsedCountry, parsedCodes)

        when: "apply Wiki token parser"
            def result = tokenParser.apply(_ as String)

        then: "apply country parser"
            1 * countryParser.apply(_ as String) >> parsedCountry

        then: "apply code parser"
            1 * codeParser.apply(_ as String) >> parsedCodes

        expect:
            result == expectedResult
    }

    def "throw ParsingException if parsed codes are empty"() {

        given: "invalid country token"
            def parsedCountry = 'Kazakhstan'
            def parsedCodes = []

        when: "apply Wiki token parser"
            tokenParser.apply(_ as String)

        then: "apply country parser"
            1 * countryParser.apply(_ as String) >> parsedCountry

        then: "apply code parser"
            1 * codeParser.apply(_ as String) >> parsedCodes

        and: "ParsingException thrown"
            def exception = thrown(ParsingException)
            exception.message == CODE_PARSING_ERROR
    }

    def buildCodeCountryTuples(String country, List<String> codes) {
        Map tuples = new HashMap()
        codes.forEach({
            tuples.put(new CountryCode(it), new Country(country))
        })
        tuples
    }
}
