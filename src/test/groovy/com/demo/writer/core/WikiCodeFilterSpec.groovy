package com.demo.writer.core

import spock.lang.Specification

class WikiCodeFilterSpec extends Specification {

    WikiCodeFilter filter = new WikiCodeFilter()

    def "should apply to valid country token"() {

        given: "Wiki country token"

        when: "apply code filter"
            def result = filter.apply(token)

        then: "success"
            result

        where:
            token                                                                                                   | _
            '[[+81]] – {{flag|Japan}}'                                                                              | _
            '[[+685]] – {{flag|Samoa}}'                                                                             | _
            '[[North American Numbering Plan|+1]] – {{flag|United States}}, including United States territories:'   | _
            '[[Telephone numbers in Puerto Rico|+1 787 / 939]] – {{flag|Puerto Rico}}'                              | _
            '[[Area code 784|+1 784]] – {{flag|Saint Vincent and the Grenadines}}'                                  | _
            '[[Telephone numbers in Kazakhstan|+7 3xx / 7xx]] – {{flag|Kazakhstan}}'                                | _
            '[[Telephone numbers in Abkhazia|+7 840 / 940]] – {{flag|Abkhazia}} - see also [[+995 44]]'             | _
            '[[+599]] – Former {{flag|Netherlands Antilles}}'                                                       | _
            '[[+599 8]] – \'\'formerly {{flag|Aruba}}'                                                              | _

    }

    def "should not apply to non-country token"() {

        given: "Wiki non-country token"

        when: "apply code filter"
            def result = filter.apply(token)

        then: "failure"
            !result

        where:
            token                                                                       | _
            '\'\'\'[[+878]] – [[Universal Personal Telecommunications]] services\'\'\'' | _
            '+858 – \'\'unassigned, formerly ANAC satellite service\'\''                | _
    }
}
