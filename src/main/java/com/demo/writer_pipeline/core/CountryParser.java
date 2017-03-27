package com.demo.writer_pipeline.core;

import com.demo.common_context.Country;
import com.demo.common_context.CountryCode;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
class CountryParser {

    Map<CountryCode, Country> apply(List<String> countryTokens) {
        //TODO:
        return new HashMap<>();
    }
}
