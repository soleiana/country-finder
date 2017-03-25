package com.demo.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
public final class ValidatedPhoneNumber {

    private final ValidatedNumberString numberString;
    private final Countries countries;

    public Country findCountry() {
        String countryName = "Latvia";
        return Country.builder()
                .name(countryName)
                .build();
    }
}
