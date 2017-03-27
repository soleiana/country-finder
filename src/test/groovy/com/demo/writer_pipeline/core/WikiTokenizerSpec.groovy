package com.demo.writer_pipeline.core

import com.demo.writer_pipeline.exceptions.TokenizingException
import spock.lang.Specification

class WikiTokenizerSpec extends Specification {

    static final TOKENIZING_ERROR = 'unable to tokenize country string'

    def codeFilter = Mock(WikiCodeFilter)
    def countryFilter = Mock(WikiCountryFilter)
    def filters = [codeFilter, countryFilter] as Set
    def rawTokenizer = Mock(WikiRawStringTokenizer)

    WikiTokenizer tokenizer = new WikiTokenizer(filters, rawTokenizer)

    def "should tokenize Wiki text"() {

        given: "well-formed wiki text"
            def wikiCountries = "n* [[+45]] – {{flag|Denmark}}\n* [[+46]] – {{flag|Sweden}}"
            def firstToken = "[[+45]] – {{flag|Denmark}}"
            def secondToken = "[[+46]] – {{flag|Sweden}"

        when:
            def result = tokenizer.apply(wikiCountries)

        then: "apply Wiki raw string tokenizer"
            1 * rawTokenizer.apply(_ as String) >> [firstToken, secondToken]

        then: "apply Wiki filters for the first token"
            1 * codeFilter.apply(firstToken) >> true
            1 * countryFilter.apply(firstToken) >> true

        then: "apply Wiki filters for the second token"
            1 * codeFilter.apply(secondToken) >> true
            1 * countryFilter.apply(secondToken) >> true

        then: "return list of valid country tokens"
            result.get(0) == "[[+45]] – {{flag|Denmark}}"
            result.get(1) == "[[+46]] – {{flag|Sweden}"

    }

    def "should return only valid country tokens"() {

        given: "well-formed wiki text"
            def wikiCountries = "n* [[+45]] – {{flag|Denmark}}\n*  +997 – ''unassigned''"
            def validCountryToken = "[[+45]] – {{flag|Denmark}}"
            def nonCountryToken = "+997 – ''unassigned''"

        when:
            def result = tokenizer.apply(wikiCountries)

        then: "apply Wiki raw string tokenizer"
             1 * rawTokenizer.apply(_ as String) >> [validCountryToken, nonCountryToken]

        then: "apply Wiki filters for the first token"
             1 * codeFilter.apply(validCountryToken) >> true
             1 * countryFilter.apply(validCountryToken) >> true

        then: "apply code filter for the second token"
            1 * codeFilter.apply(nonCountryToken) >> false

        then: "country filter is not invoked for non country token"
            0 * countryFilter.apply(nonCountryToken)

        then: "return list of valid country tokens"
            result.size() == 1
            result.get(0) == "[[+45]] – {{flag|Denmark}}"
    }

    def "should throw TokenizingException"() {

        when:
            tokenizer.apply(_ as String)

        then: "invoke Wiki raw string tokenizer and throw exception"
            1 * rawTokenizer.apply(_ as String) >> {throw new RuntimeException("")}

        then: "TokenizingException thrown"
            def exception = thrown(TokenizingException)
            exception.message == TOKENIZING_ERROR
    }

}
