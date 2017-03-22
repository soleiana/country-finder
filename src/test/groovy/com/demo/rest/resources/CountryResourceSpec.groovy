package com.demo.rest.resources

import com.demo.communications.GetCountryByPhoneNumberRequest
import com.demo.communications.GetCountryByPhoneNumberResponse
import spock.lang.Specification

class CountryResourceSpec extends Specification {

    static final PHONE_NUMBER = '+37126394806'
    def requestFactory = Stub(RequestFactory)
    def resource = new CountryResource(requestFactory)

    def "should find country"() {
        given:
            def request = Mock(GetCountryByPhoneNumberRequest)
            def response = Mock(GetCountryByPhoneNumberResponse)
            requestFactory.of(_ as String) >> request

        when: "user inputs correct international phone number"
            def country = resource.getCountryByPhoneNumber(PHONE_NUMBER)

        then:
            1 * request.execute() >> response
        then:
            1 * response.toCountry() >> new Country('Latvia')

        and: "country is found"
            country.getName() == 'Latvia'

    }
}
