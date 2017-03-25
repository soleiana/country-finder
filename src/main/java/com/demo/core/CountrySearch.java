package com.demo.core;

import com.demo.exceptions.CountrySearchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNumeric;

@Component
class CountrySearch {

    private static final String INVALID_COUNTRY_SEARCH_FORMAT = "phone number should contain only digits";
    private final Countries countries;

    @Autowired
    CountrySearch(Countries countries) {
        this.countries = countries;
    }

    Country apply(String phoneNumber) {
       checkFormat(phoneNumber);
       return countries.searchByPhoneNumber(phoneNumber);
    }

    private void checkFormat(String phoneNumber) {
        try {
            checkArgument(isNumeric(phoneNumber));
        } catch (IllegalArgumentException exception) {
            throw new CountrySearchException(INVALID_COUNTRY_SEARCH_FORMAT);
        }
    }
}
