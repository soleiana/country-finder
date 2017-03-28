package com.demo.writer_pipeline.core;

import com.demo.common_context.Country;
import com.demo.common_context.CountryCode;
import com.demo.writer_pipeline.exceptions.ParsingException;
import com.google.common.collect.ImmutableMap;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toMap;

@Log
@Component
class WikiTokenParser {

    private static final String COUNTRY_PARSING_ERROR = "unable to parse country";
    private static final String CODE_PARSING_ERROR = "unable to parse country code";

    private final WikiCodeParser codeParser;
    private final WikiCountryParser countryParser;

    @Autowired
    WikiTokenParser(WikiCodeParser codeParser, WikiCountryParser countryParser) {
        this.codeParser = codeParser;
        this.countryParser = countryParser;
    }

    Map<CountryCode, Country> apply(String token) {
        try {
            String parsedCountry = countryParser.apply(token);
            List<String> parsedCodes = codeParser.apply(token);
            checkArguments(parsedCountry, parsedCodes);

            Map<CountryCode, Country> codeCountryTuples = parsedCodes.stream()
                    .map(CountryCode::new)
                    .flatMap(code -> Stream.of(parsedCountry)
                            .map(Country::new)
                            .map(country -> new AbstractMap.SimpleImmutableEntry<>(code, country)))
                    .collect(toMap(entry -> entry.getKey(), entry -> entry.getValue()));

            codeCountryTuples.entrySet()
                    .forEach(tuple -> log.info(tuple.toString()));
            return ImmutableMap.copyOf(codeCountryTuples);

        } catch (RuntimeException exception) {
            throw new ParsingException(exception.getMessage());

        }
    }

    private void checkArguments(String country, List<String> codes) {
        checkArgument(!country.isEmpty(), COUNTRY_PARSING_ERROR);
        checkArgument(!codes.isEmpty(), CODE_PARSING_ERROR);
    }

}
