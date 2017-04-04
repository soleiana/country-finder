package com.demo.writer.core;

import com.demo.common.Country;
import com.demo.common.CountryCode;
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
