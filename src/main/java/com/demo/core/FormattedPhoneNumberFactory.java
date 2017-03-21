package com.demo.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
class FormattedPhoneNumberFactory {

    private final Set<? extends ValidationRule> validationRules;

    @Autowired
    FormattedPhoneNumberFactory(Set<? extends ValidationRule> validationRules) {
        this.validationRules = validationRules;
    }

    FormattedPhoneNumber of(String phoneNumber) {
        return FormattedPhoneNumber.builder()
                .number(phoneNumber)
                .validationRules(validationRules)
                .build();
    }
}
