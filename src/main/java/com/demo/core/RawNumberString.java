package com.demo.core;

import com.demo.exceptions.FormatException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.prependIfMissing;
import static org.apache.commons.lang3.StringUtils.removeAll;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
class RawNumberString extends PhoneNumberString {

    private static final String SPACE_CHARACTERS = "\\s";
    private static final String INTERNATIONAL_CALL_PREFIX = "+";
    private static final String EMPTY_NUMBER = "phone number is empty";

    @Builder
    RawNumberString(String phoneNumber) {
        super(phoneNumber);
    }

    RawNumberString withoutSpaceCharacters() {
        String numberWithoutSpaceCharacters = removeAll(phoneNumber, SPACE_CHARACTERS);
        return of(numberWithoutSpaceCharacters);
    }

    RawNumberString withInternationalCallPrefix() {
        String numberWithCallPrefix = addInternationalCallPrefix(phoneNumber);
        return of(numberWithCallPrefix);
    }

    RawNumberString checkFormat() {
        int prefixLength = INTERNATIONAL_CALL_PREFIX.length();
        try {
            checkArgument(phoneNumber.length() > prefixLength);
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
