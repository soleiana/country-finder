package com.demo.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/country")
public class CountryResource {

    private final SearchEngineFactory searchEngineFactory;

    @Autowired
    public CountryResource(SearchEngineFactory searchEngineFactory) {
        this.searchEngineFactory = searchEngineFactory;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Country findCountryByPhoneNumber(@NotNull @RequestParam(value = "phoneNumber") String phoneNumber){
        return searchEngineFactory.of(phoneNumber)
                .start()
                .transform();
    }
}


