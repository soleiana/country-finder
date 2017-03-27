package com.demo.writer_pipeline.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
final class RawCountries {

    @NonNull
    private final RawCountryString countryString;

    @NonNull
    private final CountryTokenizer countryTokenizer;

    @NonNull
    private final TokenizedCountryFactory tokenizedCountryFactory;

    TokenizedCountries tokenize() {
        //TODO:
        CountryTokens countryTokens = countryString.apply(countryTokenizer);
        return tokenizedCountryFactory.of(countryTokens);
    }

}
