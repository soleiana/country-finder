package com.demo.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class ValidatedPhoneNumberFactory {

    private final CountrySearch countrySearch;

    @Autowired
    public ValidatedPhoneNumberFactory(CountrySearch countrySearch) {
        this.countrySearch = countrySearch;
    }

    ValidatedPhoneNumber of(ValidatedNumberString numberString) {
        return ValidatedPhoneNumber.builder()
                .numberString(numberString)
                .countrySearch(countrySearch)
                .build();
    }
}
