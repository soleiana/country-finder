package com.demo.rest.resources;

import com.demo.rest.resources.domain.Country;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/country")
public class CountryResource {

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Country getCountryByPhoneNumber(@RequestParam(value = "phoneNumber") String phoneNumber){
        return Country.builder()
                .name("Latvia")
                .build();
    }
}


