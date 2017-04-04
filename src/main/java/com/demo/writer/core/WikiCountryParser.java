package com.demo.writer.core;

import org.springframework.stereotype.Component;

@Component
class WikiCountryParser {

    private static final String START_OF_COUNTRY_NAME_SUBSTRING = "{{flag|";
    private static final String END_OF_COUNTRY_NAME_SUBSTRING = "}";

    String apply(String token) {
        int start = token.indexOf(START_OF_COUNTRY_NAME_SUBSTRING);
        int finish = token.indexOf(END_OF_COUNTRY_NAME_SUBSTRING);
        return token.substring(start + START_OF_COUNTRY_NAME_SUBSTRING.length(), finish);
    }
}
