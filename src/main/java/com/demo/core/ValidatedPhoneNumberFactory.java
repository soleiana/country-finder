package com.demo.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class ValidatedPhoneNumberFactory {

    private final Countries countries;

    @Autowired
    public ValidatedPhoneNumberFactory(Countries countries) {
        this.countries = countries;
    }

    ValidatedPhoneNumber of(PhoneNumberString numberString) {
        return ValidatedPhoneNumber.builder()
                .numberString(numberString)
                .countries(countries)
                .build();
    }
}
