package com.demo.writer.core;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(1)
@Component
class WikiCodeFilter extends Filter {

    private static final String CODE_FILTER_REGEX = "\\[\\[([a-z0-9\\s]{3,}\\|)?\\+[0-9/\\sx]{1,}\\]\\].*";
    private static final Pattern CODE_FILTER = Pattern.compile(CODE_FILTER_REGEX);

    @Override
    boolean apply(String entry) {
        String normalizedEntry = entry.toLowerCase().trim();
        Matcher matcher = CODE_FILTER.matcher(normalizedEntry);
        return matcher.matches();
    }
}
