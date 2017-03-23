package com.demo.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public final class ValidatedPhoneNumber extends PhoneNumber {

    @Builder
    ValidatedPhoneNumber(PhoneNumberString numberString) {
        super(numberString);
    }

    public Country findCountry() {
        String countryName = "Latvia";
        return Country.builder()
                .name(countryName)
                .build();
    }
}
