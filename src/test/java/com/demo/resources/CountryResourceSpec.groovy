package com.demo.resources

import spock.lang.Specification


class CountryResourceSpec extends Specification {

    def resource = new CountryResource()

    void setup() {
    }

    def "should return country"() {
        when: "user inputs correct international phone number"
            def country = resource.getCountryByPhone('37126394806')

        then: "phone number registration country should be defined"
            country.name == 'Latvia'

    }
}
