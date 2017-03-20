package com.demo.rest.resources

import spock.lang.Specification

class CountryResourceSpec extends Specification {

    def resource = new CountryResource()

    void setup() {
    }

    def "should return country"() {
        when: "user inputs correct international phone number"
            def country = resource.getCountryByPhone('+37126394806')

        then: "phone number registration country is defined"
            country.getName() == 'Latvia'

    }
}
