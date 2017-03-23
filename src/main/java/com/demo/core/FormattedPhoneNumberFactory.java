package com.demo.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
class FormattedPhoneNumberFactory {

    private final ValidatedPhoneNumberFactory validatedPhoneNumberFactory;
    private final Set<? extends FinalValidationRule> finalValidationRules;
    private final BasicValidationRule basicValidationRule;

    @Autowired
    FormattedPhoneNumberFactory(ValidatedPhoneNumberFactory validatedPhoneNumberFactory,
                                BasicValidationRule basicValidationRule,
                                Set<? extends FinalValidationRule> finalValidationRules) {
        this.validatedPhoneNumberFactory = validatedPhoneNumberFactory;
        this.basicValidationRule = basicValidationRule;
        this.finalValidationRules = finalValidationRules;
    }

    FormattedPhoneNumber of(String phoneNumber) {
        return FormattedPhoneNumber.builder()
                .number(phoneNumber)
                .validatedPhoneNumberFactory(validatedPhoneNumberFactory)
                .basicValidationRule(basicValidationRule)
                .finalValidationRules(finalValidationRules)
                .build();
    }
}
