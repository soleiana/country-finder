package com.demo.writer_pipeline.core

import spock.lang.Specification

class WikiRawStringTokenizerSpec extends Specification {

    def tokenizer = new WikiRawStringTokenizer()

    def "should tokenize Wiki string"() {

        given: "well-formed wiki text"
            def wikiCountries = WikiCountries.TEXT

        when: "apply Wiki raw string tokenizer"
            def result = tokenizer.apply(wikiCountries)

        then: "success"
            result.get(1) == WikiCountries.SECOND_TOKEN
            result.get(2) == WikiCountries.THIRD_TOKEN
            result.last() == WikiCountries.LAST_TOKEN
    }
}
