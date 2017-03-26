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
    private final CountryParser countryParser;

    @NonNull
    private final ParsedCountryFactory parsedCountryFactory;

    ParsedCountries parse() {
        //TODO: add some logic here before call to parser
        ParsedCountryMap countryMap = countryString.apply(countryParser);
        return parsedCountryFactory.of(countryMap);
    }

}
