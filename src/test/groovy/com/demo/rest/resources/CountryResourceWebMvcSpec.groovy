package com.demo.rest.resources

import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class CountryResourceWebMvcSpec extends Specification {

    final PHONE_NUMBER = '+37126394806'
    def resource = Stub(CountryResource)

    MockMvc mvc = standaloneSetup(resource)
            .setMessageConverters(new MappingJackson2HttpMessageConverter())
            .build()

    def "should return country in JSON and response status OK"() {
        given:
            resource.getCountryByPhoneNumber(PHONE_NUMBER) >> new Country('Latvia')

        when: "user sends phone number"
            resource.getCountryByPhoneNumber(PHONE_NUMBER)

        then: "return country in JSON and response status OK"
            mvc.perform(MockMvcRequestBuilders.get("/country?phoneNumber=${PHONE_NUMBER}"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("{\"name\":\"Latvia\"}"))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    }
}
