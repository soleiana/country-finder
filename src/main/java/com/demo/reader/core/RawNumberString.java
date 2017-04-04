package com.demo.reader.core;

import com.demo.reader.exceptions.FormatException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.*;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
class RawNumberString extends PhoneNumberString {

    private static final String INTERNATIONAL_CALL_PREFIX = "+";
    private static final String EMPTY_NUMBER = "phone number is empty";
    private static final char[] NON_NUMERIC_PHONE_NUMBER_CHARACTERS = {'+', '.', '(', ')', '-', 'x', 'X'};

    @Builder
    RawNumberString(String phoneNumber) {
        super(phoneNumber);
    }

    RawNumberString withoutSpaceCharacters() {
        String numberWithoutSpaceCharacters = deleteWhitespace(phoneNumber);
        return of(numberWithoutSpaceCharacters);
    }

    RawNumberString withInternationalCallPrefix() {
        String numberWithCallPrefix = addInternationalCallPrefix(phoneNumber);
        return of(numberWithCallPrefix);
    }

    RawNumberString checkFormat() {
        try {
            checkArgument(!isEmpty(phoneNumber) &&
                    !containsWhitespace(phoneNumber) &&
                    !containsOnly(phoneNumber, NON_NUMERIC_PHONE_NUMBER_CHARACTERS));
        } catch (IllegalArgumentException exception) {
            throw new FormatException(EMPTY_NUMBER);
        }
        return of(phoneNumber);
    }

    FormattedNumberString build() {
        return FormattedNumberString.builder()
                .phoneNumber(phoneNumber)
                .build();
    }

    private String addInternationalCallPrefix(String phoneNumber) {
        return prependIfMissing(phoneNumber, INTERNATIONAL_CALL_PREFIX);
    }

    private RawNumberString of(String phoneNumber) {
        return RawNumberString.builder()
                .phoneNumber(phoneNumber)
                .build();
    }

}
