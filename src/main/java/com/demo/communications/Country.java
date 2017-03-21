package com.demo.communications;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Country {

    @NonNull
    private String name;

    GetCountryByPhoneNumberResponse toResponse() {
        return GetCountryByPhoneNumberResponse.builder()
                .country(this)
                .build();
    }
}
