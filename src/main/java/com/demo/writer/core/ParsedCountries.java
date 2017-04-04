package com.demo.writer.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
class ParsedCountries {

    @NonNull
    private final ParsedCountryMap parsedCountryMap;

    @NonNull
    private final CountrySerializer countrySerializer;

    void serialize() {
        parsedCountryMap.apply(countrySerializer);
    }
}
