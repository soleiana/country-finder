package com.demo.core;

import com.demo.exceptions.FormatException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import static com.google.common.base.Preconditions.checkArgument;

@Value
@EqualsAndHashCode(callSuper = true)
public class RawPhoneNumber extends PhoneNumber {

    private static final String EMPTY_NUMBER_MESSAGE = "phone number is empty";

    @Builder
    public RawPhoneNumber(String number) {
        super(number);
    }

    public FormattedPhoneNumber format() {
        String trimmedNumber = number.trim();
        String numberWithoutLeadingPlus = removeLeadingPlus(trimmedNumber).trim();

        try {
            checkArgument(!numberWithoutLeadingPlus.isEmpty(), EMPTY_NUMBER_MESSAGE);
        } catch (IllegalArgumentException exception) {
            throw new FormatException(exception.getMessage());
        }
        return FormattedPhoneNumber.builder()
                .number(numberWithoutLeadingPlus)
                .build();
    }

    private String removeLeadingPlus(String phoneNumber) {
        return phoneNumber.startsWith("+") ? phoneNumber.substring(1) : phoneNumber;
    }
}
