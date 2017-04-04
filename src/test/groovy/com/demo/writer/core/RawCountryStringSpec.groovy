package com.demo.writer.core

import spock.lang.Specification


class RawCountryStringSpec extends Specification {

    def tokenizer = Mock(CountryTokenizer)

    def "should apply country tokenizer"() {

        given: "well-formed raw countries"
            def countries = '[[+353]] – {{flag|Ireland}}\\n* [[+354]] – {{flag|Iceland}}'
            def rawCountryString = new RawCountryString(countries)

        when: "apply country tokenizer"
            CountryTokens tokens = rawCountryString.apply(tokenizer)

        then: "delegate tokenizing"
            1 * tokenizer.apply(_ as String) >> ['[[+353]] – {{flag|Ireland}}', '[[+354]] – {{flag|Iceland}}']

        and: "tokens created"
            tokens != null
    }
}
