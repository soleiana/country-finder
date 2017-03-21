package com.demo.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Set;

@Value
@EqualsAndHashCode(callSuper = true)
public class FormattedPhoneNumber extends PhoneNumber {

    private final Set<? extends ValidationRule> validationRules;

    @Builder
    FormattedPhoneNumber(String number, Set<? extends ValidationRule> validationRules) {
        super(number);
        this.validationRules = validationRules;
    }

    public ValidatedPhoneNumber validate() {
        return ValidatedPhoneNumber.builder()
                .number(number)
                .build();
    }

    private void validateLength() {
    }
}
