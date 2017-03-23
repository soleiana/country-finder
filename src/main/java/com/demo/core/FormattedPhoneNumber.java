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

    private final ValidatedPhoneNumberFactory validatedPhoneNumberFactory;
    private final BasicValidationRule basicValidationRule;
    private final Set<? extends FinalValidationRule> finalValidationRules;

    @Builder
    FormattedPhoneNumber(String number,
                         ValidatedPhoneNumberFactory validatedPhoneNumberFactory,
                         BasicValidationRule basicValidationRule,
                         Set<? extends FinalValidationRule> finalValidationRules) {
        super(number);
        this.validatedPhoneNumberFactory = validatedPhoneNumberFactory;
        this.finalValidationRules = finalValidationRules;
        this.basicValidationRule = basicValidationRule;
    }

    public ValidatedPhoneNumber validate() {
        basicValidationRule.apply(number);
        boolean validationResult = finalValidationRules.stream()
                .map(rule -> rule.apply(number))
                .anyMatch(result -> result);

        if (!validationResult) {
            throw new ValidationException(INVALID_PHONE_NUMBER_FORMAT);
        }
        return validatedPhoneNumberFactory.of(number);
    }
}
