package com.demo.core

import com.demo.exceptions.SearchException
import spock.lang.Specification


class CountrySearchSpec extends Specification {

    static final INVALID_PHONE_NUMBER_FORMAT = 'phone number should contain only digits'

    def countryRegister = Mock(CountryRegister)
    CountrySearch countrySearch = new CountrySearch(countryRegister)

    def "should find country in register"() {

        given: "number containing only digits"
            def country = new Country('Latvia')

        when: "apply country search"
            def result = countrySearch.apply(phoneNumber)

        then: "search country in register"
            1 * countryRegister.searchCountry(_ as String) >> country

        and: "country found"
            result == country

        where:
            phoneNumber         | _
            '3712345678'        | _
            '123456'            | _
            '1234567890123456'  | _
    }

    def "should throw SearchException"() {

        given: "invalid number for country search"

        when: "apply country search"
            countrySearch.apply(phoneNumber)

        then: "SearchException thrown"
            def exception = thrown(SearchException)
            exception.message == INVALID_PHONE_NUMBER_FORMAT

        where:
            phoneNumber         | _
            '+3712345678'       | _
            ''                  | _
            '123456789A'        | _
    }

}
