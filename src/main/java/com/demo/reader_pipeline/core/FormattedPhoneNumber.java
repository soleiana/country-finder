package com.demo.reader_pipeline.core;

import com.demo.reader_pipeline.exceptions.ValidationException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Builder
@ToString
@EqualsAndHashCode
public final class FormattedPhoneNumber {

    private static final String INVALID_PHONE_NUMBER_FORMAT = "invalid phone number format";

    private final FormattedNumberString numberString;

    private final ValidatedPhoneNumberFactory validatedPhoneNumberFactory;
    private final BasicValidationRule basicValidationRule;
    private final Set<? extends FinalValidationRule> finalValidationRules;

    public ValidatedPhoneNumber validate() {
        ValidatedNumberString validatedNumberString = numberString.withoutSpecialCharacters()
                .withoutExtension()
                .build();

        validatedNumberString.apply(basicValidationRule);
        boolean validationResult = finalValidationRules.stream()
                .anyMatch(numberString::apply);

        if (!validationResult) {
            throw new ValidationException(INVALID_PHONE_NUMBER_FORMAT);
        }
        return validatedPhoneNumberFactory.of(validatedNumberString);
    }
}
