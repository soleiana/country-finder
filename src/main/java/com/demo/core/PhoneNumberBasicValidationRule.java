package com.demo.core;

import com.demo.exceptions.ValidationException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(1)
@Component
class PhoneNumberBasicValidationRule implements ValidationRule {

    private static final int MIN_DIGITS_IN_USE = 7;
    private static final int MAX_DIGITS_ITU_T = 15;
    private static final String INVALID_BASIC_FORMAT = "invalid basic format";
    private static final Pattern PHONE_NUMBER_SPECIAL_CHARACTERS = Pattern.compile("[\\+\\.\\-\\(\\)\\s]");

    private static Pattern basicPhoneNumber;

    static {
        String regex = "^[0-9]{" + MIN_DIGITS_IN_USE + "," + MAX_DIGITS_ITU_T + "}$";
        basicPhoneNumber = Pattern.compile(regex);
    }

    @Override
    public boolean apply(String phoneNumber) {
        String numberWithoutSpecialChars = removeSpecialCharacters(phoneNumber);
        String numberWithoutExtension = removeExtension(numberWithoutSpecialChars);

        Matcher numberMatcher = basicPhoneNumber.matcher(numberWithoutExtension);
        if (!numberMatcher.matches()) {
            throw new ValidationException(INVALID_BASIC_FORMAT);
        }
        return true;
    }

    private String removeSpecialCharacters(String phoneNumber) {
        return PHONE_NUMBER_SPECIAL_CHARACTERS.matcher(phoneNumber)
                .replaceAll("");
    }

    private String removeExtension(String phoneNumber) {
        final char EXTENSION_DELIMITER = 'x';
        int indexOfExtension = phoneNumber.lastIndexOf(EXTENSION_DELIMITER);
        return indexOfExtension > -1 ? phoneNumber.substring(0, indexOfExtension - 1) : phoneNumber;
    }
}
