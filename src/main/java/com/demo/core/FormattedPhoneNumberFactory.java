package com.demo.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
class FormattedPhoneNumberFactory {

    private final Set<? extends PhoneNumberValidationRule> validationRules;

    @Autowired
    FormattedPhoneNumberFactory(Set<? extends PhoneNumberValidationRule> validationRules) {
        this.validationRules = validationRules;
    }

    FormattedPhoneNumber of(String phoneNumber) {
        return FormattedPhoneNumber.builder()
                .number(phoneNumber)
                .validationRules(validationRules)
                .build();
    }
}
