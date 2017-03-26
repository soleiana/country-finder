package com.demo.writer_pipeline.core;

import com.demo.writer_pipeline.communications.Countries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class RawCountryFactory {

    private final CountryParser countryParser;
    private final ParsedCountryFactory parsedCountryFactory;

    @Autowired
    RawCountryFactory(CountryParser countryParser,
                      ParsedCountryFactory parsedCountryFactory) {
        this.countryParser = countryParser;
        this.parsedCountryFactory = parsedCountryFactory;
    }

    RawCountries of(Countries countries) {
        RawCountryString rawString = ofCountries(countries);
        return RawCountries.builder()
                .countryString(rawString)
                .countryParser(countryParser)
                .parsedCountryFactory(parsedCountryFactory)
                .build();

    }
    private RawCountryString ofCountries(Countries countries) {
        return countries.toRawString();
    }
}
