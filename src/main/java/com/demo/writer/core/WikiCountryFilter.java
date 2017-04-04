package com.demo.writer.core;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(5)
@Component
class WikiCountryFilter extends Filter {

    private static final String COUNTRY_REGEX = "\\]\\].{1,}\\{\\{flag\\|.+\\}\\}";
    private static final Pattern COUNTRY_FILTER = Pattern.compile(COUNTRY_REGEX);
    private static final Pattern NOT_IN_USE_FILTER = Pattern.compile("(not yet in use|assigned .+but not in use|assigned but uses)");
    private static final String FORMER_COUNTRY_REGEX = "(formerly|former)\\s\\{\\{flag\\|.+\\}\\}";
    private static final Pattern DISCONTINUED_FILTER = Pattern.compile("discontinued");
    private static final Pattern FORMER_COUNTRY_FILTER = Pattern.compile(FORMER_COUNTRY_REGEX);

    @Override
    boolean apply(String entry) {
        String normalizedEntry = entry.toLowerCase();
        Matcher country = COUNTRY_FILTER.matcher(normalizedEntry);
        Matcher formerCountry = FORMER_COUNTRY_FILTER.matcher(normalizedEntry);
        Matcher discontinued = DISCONTINUED_FILTER.matcher(normalizedEntry);
        Matcher notInUse = NOT_IN_USE_FILTER.matcher(normalizedEntry);
        return country.find() && !notInUse.find() && !formerCountry.find() && !discontinued.find();
    }
}
