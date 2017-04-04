package com.demo.writer.core;

import com.demo.writer.exceptions.TokenizingException;
import com.google.common.collect.ImmutableList;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log
@Component
class WikiTokenizer extends CountryTokenizer {

    private static final String TOKENIZING_ERROR = "unable to tokenize country string";

    private final Set<? extends Filter> filters;
    private final WikiRawStringTokenizer rawStringTokenizer;

    @Autowired
    WikiTokenizer(Set<? extends Filter> filters, WikiRawStringTokenizer rawStringTokenizer) {
        this.filters = filters;
        this.rawStringTokenizer = rawStringTokenizer;
    }

    @Override
    List<String> apply(String countryString) {
        List<String> filteredTokens;
        try {
            List<String> tokens = rawStringTokenizer.apply(countryString);
            filteredTokens = filter(tokens);
        } catch (RuntimeException exception) {
            throw new TokenizingException(TOKENIZING_ERROR);
        }
        if (filteredTokens.isEmpty()) {
            throw new TokenizingException(TOKENIZING_ERROR);
        }
        log.info("FILTERED COUNTRIES");
        filteredTokens.forEach(log::info);
        return ImmutableList.copyOf(filteredTokens);
    }

    private List<String> filter(List<String> tokens) {
        return tokens.stream()
                .filter(token -> filters.stream()
                            .allMatch(wikiFilter -> wikiFilter.apply(token)))
                .collect(Collectors.toList());

    }
}
