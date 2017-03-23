package com.demo.core;

import com.demo.exceptions.ValidationException;
import lombok.Builder;

import javax.validation.constraints.NotNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Builder
class PhoneNumberRegex {

    @NotNull
    private final String phoneNumber;

    @NotNull
    private final Pattern regexPattern;

    @NotNull
    private final String errorMessage;

    boolean applyWithException() {
        Matcher numberMatcher = regexPattern.matcher(phoneNumber);
        if (!numberMatcher.matches()) {
            throw new ValidationException(errorMessage);
        }
        return true;
    }

    boolean apply() {
        Matcher numberMatcher = regexPattern.matcher(phoneNumber);
        return numberMatcher.matches();
    }
}
