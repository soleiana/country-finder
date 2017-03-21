package com.demo.core;

import com.demo.exceptions.FormatException;
import org.springframework.stereotype.Component;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import static org.apache.commons.lang3.StringUtils.prependIfMissing;

@Component
class PhoneNumberFormatter {
    private static final String EMPTY_NUMBER_MESSAGE = "phone number is empty";

    String apply(String phoneNumber) {
        String normalizedNumberWithLeadingPlus = prependIfMissing(normalizeSpace(phoneNumber), "+");
        try {
            checkArgument(normalizedNumberWithLeadingPlus.length() > 1, EMPTY_NUMBER_MESSAGE);
        } catch (IllegalArgumentException exception) {
            throw new FormatException(exception.getMessage());
        }
        return normalizedNumberWithLeadingPlus;
    }
}
