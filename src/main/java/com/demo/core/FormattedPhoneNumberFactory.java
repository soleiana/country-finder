package com.demo.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
class FormattedPhoneNumberFactory extends PhoneNumberFactory {

    private final ValidatedPhoneNumberFactory validatedPhoneNumberFactory;
    private final Set<? extends FinalValidationRule> finalValidationRules;
    private final BasicValidationRule basicValidationRule;

    @Autowired
    FormattedPhoneNumberFactory(ValidatedPhoneNumberFactory validatedPhoneNumberFactory,
                                PhoneNumberStringFactory phoneNumberStringFactory,
                                BasicValidationRule basicValidationRule,
                                Set<? extends FinalValidationRule> finalValidationRules) {

        super(phoneNumberStringFactory);
        this.validatedPhoneNumberFactory = validatedPhoneNumberFactory;
        this.basicValidationRule = basicValidationRule;
        this.finalValidationRules = finalValidationRules;
    }

    FormattedPhoneNumber of(String phoneNumber) {
        PhoneNumberString numberString = phoneNumberStringFactory.of(phoneNumber);
        return FormattedPhoneNumber.builder()
                .numberString(numberString)
                .validatedPhoneNumberFactory(validatedPhoneNumberFactory)
                .basicValidationRule(basicValidationRule)
                .finalValidationRules(finalValidationRules)
                .build();
    }
}
