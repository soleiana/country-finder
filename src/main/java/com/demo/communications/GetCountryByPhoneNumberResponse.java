package com.demo.communications;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class GetCountryByPhoneNumberResponse {

    @NonNull
    private final Country country;

    public com.demo.rest.resources.Country toCountry() {
        return com.demo.rest.resources.Country.builder()
                .name(country.getName())
                .build();
    }
}
