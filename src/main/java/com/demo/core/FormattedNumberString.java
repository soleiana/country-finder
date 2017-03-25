package com.demo.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static org.apache.commons.lang3.StringUtils.removeAll;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
class FormattedNumberString extends PhoneNumberString {

    private static final String PHONE_NUMBER_SPECIAL_CHARACTERS = "[\\.\\-\\(\\)\\s]";
    private static final char PHONE_NUMBER_EXTENSION_DELIMITER = 'x';

    @Builder
    FormattedNumberString(String phoneNumber) {
        super(phoneNumber);
    }

    boolean apply(FinalValidationRule validationRule) {
        return validationRule.apply(phoneNumber);
    }

    FormattedNumberString withoutSpecialCharacters() {
        String numberWithoutSpecialCharacters = removeAll(phoneNumber, PHONE_NUMBER_SPECIAL_CHARACTERS);
        return of(numberWithoutSpecialCharacters);
    }

    FormattedNumberString withoutExtension() {
        int indexOfExtension = phoneNumber.toLowerCase().indexOf(PHONE_NUMBER_EXTENSION_DELIMITER);
        String numberWithoutExtension = indexOfExtension > -1 ? phoneNumber.substring(0, indexOfExtension) : phoneNumber;
        return of(numberWithoutExtension);
    }

    ValidatedNumberString build() {
        return ValidatedNumberString.builder()
                .phoneNumber(phoneNumber)
                .build();
    }

    private FormattedNumberString of(String phoneNumber) {
        return FormattedNumberString.builder()
                .phoneNumber(phoneNumber)
                .build();
    }
}
