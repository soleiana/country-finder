package com.demo.writer_pipeline.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
final class ParsedCountries {

    @NonNull
    private final ParsedCountryMap parsedCountryMap;

    @NonNull
    private final CountrySerializer countrySerializer;

    void serialize() {
        parsedCountryMap.apply(countrySerializer);
    }
}
