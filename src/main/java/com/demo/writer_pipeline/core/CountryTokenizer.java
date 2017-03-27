package com.demo.writer_pipeline.core;

import java.util.List;

abstract class CountryTokenizer {

    abstract List<String> apply(String countryString);
}
