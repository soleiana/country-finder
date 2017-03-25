package com.demo.core;

import com.demo.exceptions.SearchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNumeric;

@Component
class CountrySearch {

    private static final String INVALID_PHONE_NUMBER_FORMAT = "phone number should contain only digits";
    private final CountryRegister countryRegister;

    @Autowired
    CountrySearch(CountryRegister countryRegister) {
        this.countryRegister = countryRegister;
    }

    Country apply(String phoneNumber) {
        checkFormat(phoneNumber);
        return countryRegister.searchCountry(phoneNumber);
    }

    private void checkFormat(String phoneNumber) {
        try {
            checkArgument(isNumeric(phoneNumber));
        } catch (IllegalArgumentException exception) {
            throw new SearchException(INVALID_PHONE_NUMBER_FORMAT);
        }
    }
}
