package com.demo.writer.core;

import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;

@Component
class WikiRawStringTokenizer {

    private static final String SPLIT_REGEX = "\\n\\*\\*?\\s";

    List<String> apply(String countryString) {
        List<String> tokens = asList(countryString.split(SPLIT_REGEX));
        return ImmutableList.copyOf(tokens);
    }
}
