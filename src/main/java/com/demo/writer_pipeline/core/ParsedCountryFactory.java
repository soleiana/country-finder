package com.demo.writer_pipeline.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class ParsedCountryFactory {

    private final CountrySerializer countrySerializer;

    @Autowired
    ParsedCountryFactory(CountrySerializer countrySerializer) {
        this.countrySerializer = countrySerializer;
    }

    ParsedCountries of(ParsedCountryMap countryMap) {
        return ParsedCountries.builder()
                .countrySerializer(countrySerializer)
                .parsedCountryMap(countryMap)
                .build();
    }
}
