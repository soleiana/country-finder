package com.demo.writer.core;

import com.demo.common.Country;
import com.demo.common.CountryCode;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Map;

@Builder
@ToString
@EqualsAndHashCode
class ParsedCountryMap {

    @NonNull
    private final Map<CountryCode, Country> countryMap;

    void apply(CountrySerializer countrySerializer) {
        countrySerializer.apply(countryMap);
    }
}
