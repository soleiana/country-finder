package com.demo.writer_pipeline.core;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
class WikiCountryParser {

    private static int counter = 0;

    private static final String COUNTRY_REGEX = "(A-Za-z\\s){1,}";
    private static final Pattern COUNTRY = Pattern.compile(COUNTRY_REGEX);

    String apply(String token) {
       // Matcher country = COUNTRY.matcher(token);
        //return country.find() ? country.group(1) : EMPTY;
        counter++;
        return "Mock country" + counter;
    }
}
