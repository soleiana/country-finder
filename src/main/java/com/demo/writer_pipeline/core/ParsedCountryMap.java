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
class ParsedCountryMap {

    @NonNull
    private final Map<CountryCode, Country> countryMap;

    void apply(CountrySerializer countrySerializer) {
        countrySerializer.apply(countryMap);
    }
}
