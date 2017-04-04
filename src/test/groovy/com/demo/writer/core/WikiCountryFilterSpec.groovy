package com.demo.writer.core

import spock.lang.Specification

class WikiCountryFilterSpec extends Specification {

    def filter = new WikiCountryFilter()

    def "should apply to actual country token"() {

        given: "actual country token"

        when: "apply Wiki country filter"
            def result = filter.apply(token)

        then: "success"
            result
        where:
            token                                                                   | _
            '[[+81]] – {{flag|Japan}}'                                              | _
            '[[North American Numbering Plan|+1]] – {{flag|Canada}}'                | _
            '[[Telephone numbers in Mayotte|+262 269 / 639]] – {{flag|Mayotte}}'    | _
            '[[Telephone numbers in Kazakhstan|+7 3xx / 7xx]] – {{flag|Kazakhstan}}'| _

    }

    def "should not apply to former/ discontinued/ not in use and other non-relevant token"() {

        given: "non-relevant token"

        when: "apply Wiki country filter"
            def result = filter.apply(token)

        then: "failure"
            !result
        where:
            token                                                                               | _
            '[[+599 5]] – \'\'formerly {{flag|Sint Maarten}} – Now included in [[North American Numbering Plan|NANP]] as code [[Area code 721|+1-721]] (see Zone 1, above)\'\'' | _
            '[[+599 8]] – \'\'formerly {{flag|Aruba}} – See country code [[+297]] above\'\''    | _
            '[[+599]] – Former {{flag|Netherlands Antilles}}, now grouped as follows:'          | _
            '[[+38]] – \'\'Discontinued (was assigned to {{flag|Socialist Federal Republic of Yugoslavia}} until its break-up)\'\'' |_
            '[[North American Numbering Plan|+1]] Many, but not all, [[Caribbean]] nations and some Caribbean Dutch and [[British Overseas Territories]]:'  |_
            '[[Telephone numbers in Vatican City|+39 06 698]] – {{flag|Vatican City}} \'\'(assigned +379 but not in use)\'\''   | _
            '[[+379]] – {{flag|Vatican City}} assigned but uses Italian [[+39]] 06698.'         | _
            '{{flag|Kosovo}}{{efn|name=status}} (Assigned in 2016, not yet in use)'             | _

    }
}
