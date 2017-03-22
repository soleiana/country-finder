package com.demo.core;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
class PhoneNumberRegexFactory {

    PhoneNumberRegex of(String phoneNumber, Pattern regexPattern, String errorMessage) {
        return PhoneNumberRegex.builder()
                .phoneNumber(phoneNumber)
                .regexPattern(regexPattern)
                .errorMessage(errorMessage)
                .build();
    }
}
