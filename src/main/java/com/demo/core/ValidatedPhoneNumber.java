package com.demo.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public final class ValidatedPhoneNumber extends PhoneNumber {

    private final Countries countries;

    @Builder
    ValidatedPhoneNumber(PhoneNumberString numberString, Countries countries) {
        super(numberString);
        this.countries = countries;
    }

    public Country findCountry() {
        String countryName = "Latvia";
        return Country.builder()
                .name(countryName)
                .build();
    }
}
