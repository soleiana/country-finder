package com.demo.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class ValidatedPhoneNumber extends PhoneNumber {

    @Builder
    public ValidatedPhoneNumber(String number) {
        super(number);
    }

    public Country findCountry() {
        String countryName = "Latvia";
        return Country.builder()
                .name(countryName)
                .build();
    }
}
