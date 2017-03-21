package com.demo.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Set;

@Value
@EqualsAndHashCode(callSuper = true)
public class FormattedPhoneNumber extends PhoneNumber {

    private static final int MIN_DIGITS_IN_USE = 7;
    private static final int MAX_DIGITS_ITU_T = 15;

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
