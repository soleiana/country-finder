package com.demo.communications;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
public class GetCountryByPhoneNumberResponse {

    @NonNull
    private final Country country;

    public com.demo.rest.resources.Country toCountry() {
        return com.demo.rest.resources.Country.builder()
                .name(country.asName())
                .build();
    }
}
