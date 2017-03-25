package com.demo.core;

import com.demo.exceptions.FormatException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static org.apache.commons.lang3.StringUtils.prependIfMissing;
import static org.apache.commons.lang3.StringUtils.removeAll;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
class RawNumberString extends PhoneNumberString {

    private static final String SPACE_CHARACTERS = "\\s";
    private static final String INTERNATIONAL_CALL_PREFIX = "+";
    private static final String EMPTY_NUMBER_MESSAGE = "phone number is empty";

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
        if (phoneNumber.length() <= prefixLength) {
            throw new FormatException(EMPTY_NUMBER_MESSAGE);
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
