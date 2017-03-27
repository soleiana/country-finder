package com.demo.writer_pipeline.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;

@Builder
@ToString
@EqualsAndHashCode
public class RawCountryString {

    @NonNull
    private final String countries;

    CountryTokens apply(CountryTokenizer tokenizer) {
        List<String> tokens = tokenizer.apply(countries);
        return CountryTokens.builder()
                .countryTokens(tokens)
                .build();
    }

}
