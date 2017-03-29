package com.demo.writer_pipeline.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
class TokenizedCountries {

    @NonNull
    private final CountryTokens countryTokens;

    @NonNull
    private final CountryParser countryParser;

    @NonNull
    private final ParsedCountryFactory parsedCountryFactory;

    ParsedCountries parse() {
        ParsedCountryMap countryMap = countryTokens.apply(countryParser);
        return parsedCountryFactory.of(countryMap);
    }
}
