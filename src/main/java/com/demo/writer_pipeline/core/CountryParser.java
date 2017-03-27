package com.demo.writer_pipeline.core;

import com.demo.common_context.Country;
import com.demo.common_context.CountryCode;

import java.util.List;
import java.util.Map;

abstract class CountryParser {

    abstract Map<CountryCode, Country> apply(List<String> countryTokens);
}
