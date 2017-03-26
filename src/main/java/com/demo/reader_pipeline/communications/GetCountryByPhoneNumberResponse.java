package com.demo.reader_pipeline.communications;

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
        return country.transform();
    }
}
