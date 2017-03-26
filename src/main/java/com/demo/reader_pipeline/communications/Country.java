package com.demo.reader_pipeline.communications;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
public final class Country {

    @NonNull
    private final String name;

    GetCountryByPhoneNumberResponse toResponse() {
        return GetCountryByPhoneNumberResponse.builder()
                .country(this)
                .build();
    }

    com.demo.rest.resources.Country transform() {
        return com.demo.rest.resources.Country.builder()
                .name(name)
                .build();
    }
}
