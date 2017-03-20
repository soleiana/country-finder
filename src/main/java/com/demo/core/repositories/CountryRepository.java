package com.demo.core.repositories;

import com.demo.rest.resources.domain.Country;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
@Log
public class CountryRepository {

    public Country getCountryByPhoneNumber(String phoneNumber) {
        return null;
    }
}
