package com.demo.rest.resources

import com.demo.reader.communications.SearchEngine
import spock.lang.Specification

class CountryResourceSpec extends Specification {

    static final PHONE_NUMBER = '+37126394806'
    def requestFactory = Stub(SearchEngineFactory)
    def resource = new CountryResource(requestFactory)

    def "should find country"() {
        given:
            def searchEngine = Mock(SearchEngine)
            def latvia = new Country('Latvia')
            def country = Mock(com.demo.reader.communications.Country)
            requestFactory.of(_ as String) >> searchEngine

        when: "user inputs correct international phone number"
            def result = resource.findCountryByPhoneNumber(PHONE_NUMBER)

        then: "initiate search and find country"
            1 * searchEngine.start() >> country

        then: "transform country to web model"
            1 * country.transform() >> latvia

        and: "country found"
            result == latvia

    }
}
