package com.demo.writer.core;

import com.demo.common.Country;
import com.demo.common.CountryCode;

import java.util.List;
import java.util.Map;

abstract class CountryParser {

    abstract Map<CountryCode, Country> apply(List<String> countryTokens);
}
