package com.demo.writer_pipeline.core;

import com.demo.writer_pipeline.communications.Countries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class RawCountryFactory {

    private final CountryTokenizer countryTokenizer;
    private final TokenizedCountryFactory tokenizedCountryFactory;

    @Autowired
    public RawCountryFactory(CountryTokenizer countryTokenizer,
                             TokenizedCountryFactory tokenizedCountryFactory) {
        this.countryTokenizer = countryTokenizer;
        this.tokenizedCountryFactory = tokenizedCountryFactory;
    }

    RawCountries of(Countries countries) {
        RawCountryString rawString = ofCountries(countries);
        return RawCountries.builder()
                .countryString(rawString)
                .countryTokenizer(countryTokenizer)
                .tokenizedCountryFactory(tokenizedCountryFactory)
                .build();

    }
    private RawCountryString ofCountries(Countries countries) {
        return countries.toRawString();
    }
}