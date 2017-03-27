package com.demo.writer_pipeline.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class TokenizedCountryFactory {

    private final CountryParser countryParser;
    private final ParsedCountryFactory parsedCountryFactory;

    @Autowired
    TokenizedCountryFactory(CountryParser countryParser, ParsedCountryFactory parsedCountryFactory) {
        this.countryParser = countryParser;
        this.parsedCountryFactory = parsedCountryFactory;
    }

    TokenizedCountries of(CountryTokens countryTokens) {
        return TokenizedCountries.builder()
                .countryTokens(countryTokens)
                .countryParser(countryParser)
                .parsedCountryFactory(parsedCountryFactory)
                .build();
    }
}
