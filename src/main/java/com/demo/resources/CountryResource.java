package com.demo.resources;

import com.demo.resources.domain.Country;

public class CountryResource {

    public Country getCountryByPhone(String phoneNumber){
        return Country.builder()
                .name("Latvia")
                .build();
    }
}


