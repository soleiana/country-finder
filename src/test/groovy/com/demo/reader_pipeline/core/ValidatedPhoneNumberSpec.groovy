package com.demo.reader_pipeline.core

import com.demo.common_context.Country
import spock.lang.Specification


class ValidatedPhoneNumberSpec extends Specification {

    def numberString = Mock(ValidatedNumberString)
    def numberStringWithoutCallPrefix = Mock(ValidatedNumberString)
    def countrySearch = Mock(CountrySearch)

    def validatedPhoneNumber = new ValidatedPhoneNumber(numberString, countrySearch)

    def "should find country"() {

        given:
            def country = new Country('Latvia')

        when: "find country"
            def result = validatedPhoneNumber.findCountry()

        then: "remove international call prefix"
            1 * numberString.withoutInternationalCallPrefix() >> numberStringWithoutCallPrefix

        then: "apply country search"
            1 * numberStringWithoutCallPrefix.apply(countrySearch) >> country

        and: "country found"
            result == country
    }
}
