package com.demo.writer_pipeline.core;

import com.demo.common_context.Country;
import com.demo.common_context.CountryCode;
import com.google.common.collect.ImmutableMap;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log
@Component
class WikiParser extends CountryParser {

    private final WikiTokenParser tokenParser;

    WikiParser(WikiTokenParser tokenParser) {
        this.tokenParser = tokenParser;
    }

    @Override
    Map<CountryCode, Country> apply(List<String> countryTokens) {
        Map<CountryCode, Country> parsedCountries = new HashMap<>();

        for (String countryToken: countryTokens) {
            Map<CountryCode, Country> codeCountryTuples = tokenParser.apply(countryToken);
            if (!hasDuplicates(codeCountryTuples, parsedCountries)) {
                parsedCountries.putAll(codeCountryTuples);

            }
        }

        return ImmutableMap.copyOf(parsedCountries);
    }

    private boolean hasDuplicates(Map<CountryCode, Country> tuples, Map<CountryCode, Country> parsedCountries) {
        return parsedCountries.keySet().containsAll(tuples.keySet());

    }
}
