package com.demo.writer_pipeline.core;

import com.demo.common_context.Country;
import com.demo.common_context.CountryCode;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Builder
@ToString
@EqualsAndHashCode
class CountryTokens {

    @NonNull
    private final List<String> countryTokens;

    ParsedCountryMap apply(CountryParser countryParser) {
        Map<CountryCode, Country> countryMap = countryParser.apply(countryTokens);
        return ParsedCountryMap.builder()
                .countryMap(countryMap)
                .build();

    }

}
