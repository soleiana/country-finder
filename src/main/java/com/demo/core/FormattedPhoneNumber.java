package com.demo.core;

import com.demo.exceptions.ValidationException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Set;

@Value
@EqualsAndHashCode(callSuper = true)
public class FormattedPhoneNumber extends PhoneNumber {

    private static final String INVALID_PHONE_NUMBER_FORMAT = "invalid phone number format";
    private final Set<? extends PhoneNumberValidationRule> validationRules;

    @Builder
    FormattedPhoneNumber(String number, Set<? extends PhoneNumberValidationRule> validationRules) {
        super(number);
        this.validationRules = validationRules;
    }

    public ValidatedPhoneNumber validate() {
        boolean validationResult = validationRules.stream()
                .map(rule -> rule.apply(number))
                .anyMatch(result -> result);

        if (!validationResult) {
            throw new ValidationException(INVALID_PHONE_NUMBER_FORMAT);
        }
        return ValidatedPhoneNumber.builder()
                .number(number)
                .build();
    }
}
