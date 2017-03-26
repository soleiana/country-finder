package com.demo.reader_pipeline.core;

import com.demo.reader_pipeline.exceptions.ValidationException;
import lombok.Builder;
import lombok.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Builder
class PhoneNumberRegex {

    @NonNull
    private final String phoneNumber;

    @NonNull
    private final Pattern regexPattern;

    @NonNull
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
