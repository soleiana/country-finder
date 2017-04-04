package com.demo.writer.core

import spock.lang.Specification

class WikiCodeParserSpec extends Specification {

    def codeParser = new WikiCodeParser()

    def "should apply to single country code"() {

        given: "valid country token"
            def result = codeParser.apply(token)

        expect: "parsed token"
            result.size() == 1
            result.get(0) == expectedResult

        where:
            token                                                                               | expectedResult
             "[[North American Numbering Plan|+1]] – {{flag|United States}}"                    | '1'
             "[[+691]] – {{flag|Federated States of Micronesia}}"                               | '691'
             "[[Area code 767|+1 767]] – {{flag|Dominica}}"                                     | '1767'
             "[[Telephone numbers in the Pitcairn Islands|+64 xx]] – {{flag|Pitcairn Islands}}" | '64'
    }

    def "should apply to multiple country codes"() {

        given: "valid country token"

            def result = codeParser.apply(token)

        expect: "parsed tokens"
            result.size() == expectedResult.size()
            result.containsAll(expectedResult)
        where:
            token                                                                                               | expectedResult
            "[[Telephone numbers in Kazakhstan|+7 3xx / 7xx]]– {{flag|Kazakhstan}}"                             | ['73', '77']
            "[[Telephone numbers in Abkhazia|+7 840 / 940]] – {{flag|Abkhazia}}"                                | ['7840', '7940']
            "[[Telephone numbers in Transnistria|+373 2 / 5]] – {{flag|Transnistria}}"                          | ['3732', '3735']
            "[[Telephone numbers in Nagorno-Karabakh|+374 47 / 97]] – {{flag|Nagorno-Karabakh}}"                | ['37447', '37497']
            "[[Telephone numbers in the Dominican Republic|+1 809 / 829 / 849]] – {{flag|Dominican Republic}}"  | ['1809', '1829', '1849']

    }

}
