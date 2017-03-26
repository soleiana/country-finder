package com.demo.writer_pipeline.core;

import com.demo.common_context.Country;
import com.demo.common_context.CountryCode;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Map;

@Builder
@ToString
@EqualsAndHashCode
public class RawCountryString {

    @NonNull
    private final String countries;

    ParsedCountryMap apply(CountryParser countryParser) {
        Map<CountryCode, Country> countryMap = countryParser.apply(countries);
        return ParsedCountryMap.builder()
                .countryMap(countryMap)
                .build();

    }

}
