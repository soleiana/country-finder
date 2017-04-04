package com.demo.writer.core;

import java.util.List;

abstract class CountryTokenizer {

    abstract List<String> apply(String countryString);
}
