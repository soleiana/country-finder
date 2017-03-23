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
    FormattedPhoneNumber(PhoneNumberString numberString,
                         ValidatedPhoneNumberFactory validatedPhoneNumberFactory,
                         BasicValidationRule basicValidationRule,
                         Set<? extends FinalValidationRule> finalValidationRules) {

        super(numberString);
        this.validatedPhoneNumberFactory = validatedPhoneNumberFactory;
        this.finalValidationRules = finalValidationRules;
        this.basicValidationRule = basicValidationRule;
    }

    public ValidatedPhoneNumber validate() {
        String formattedNumber = numberString.formatForBasicValidation();
        basicValidationRule.apply(formattedNumber);
        boolean validationResult = finalValidationRules.stream()
                .map(rule -> rule.apply(numberString.asNumber()))
                .anyMatch(result -> result);

        if (!validationResult) {
            throw new ValidationException(INVALID_PHONE_NUMBER_FORMAT);
        }
        return validatedPhoneNumberFactory.of(numberString.asNumber());
    }
}
