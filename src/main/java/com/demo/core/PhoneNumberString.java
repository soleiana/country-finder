package com.demo.core;

import com.demo.exceptions.FormatException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.java.Log;

import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.prependIfMissing;

@Log
@Builder
@ToString
@EqualsAndHashCode
class PhoneNumberString {

    private static final String INTERNATIONAL_CALL_PREFIX = "+";
    private static final String EMPTY_NUMBER_MESSAGE = "phone number is empty";
    private static final Pattern PHONE_NUMBER_SPECIAL_CHARACTERS = Pattern.compile("[\\.\\-\\(\\)\\s]");
    private static final Pattern SPACE_CHARACTERS = Pattern.compile("\\s");
    private static final char PHONE_NUMBER_EXTENSION_DELIMITER = 'x';

    @NonNull
    private final String phoneNumber;

    String toNumber() {
        return phoneNumber;
    }

    boolean accept(PhoneNumberValidationRule validationRule) {
        return validationRule.apply(phoneNumber);
    }

    PhoneNumberString formatForFinalValidation() {
        String numberWithoutSpaceCharacters = removeSpecialCharacters(phoneNumber, SPACE_CHARACTERS);
        String formattedNumber = addInternationalCallPrefix(numberWithoutSpaceCharacters);
        checkFormat(formattedNumber);
        return of(formattedNumber);
    }

    PhoneNumberString formatForBasicValidation() {
        String numberWithoutSpecialCharacters = removeSpecialCharacters(phoneNumber, PHONE_NUMBER_SPECIAL_CHARACTERS);
        String formattedNumber = removeExtension(numberWithoutSpecialCharacters);
        return of(formattedNumber);
    }

    private PhoneNumberString of(String phoneNumber) {
        return PhoneNumberString.builder()
                .phoneNumber(phoneNumber)
                .build();
    }

    private String removeSpecialCharacters(String phoneNumber, Pattern charactersToRemove) {
        return charactersToRemove.matcher(phoneNumber)
                .replaceAll("");
    }

    private String removeExtension(String phoneNumber) {
        int indexOfExtension = phoneNumber.toLowerCase().indexOf(PHONE_NUMBER_EXTENSION_DELIMITER);
        return indexOfExtension > -1 ? phoneNumber.substring(0, indexOfExtension) : phoneNumber;
    }

    private String addInternationalCallPrefix(String phoneNumber) {
        return prependIfMissing(phoneNumber, INTERNATIONAL_CALL_PREFIX);
    }

    private void checkFormat(String formattedPhoneNumber) {
        int prefixLength = INTERNATIONAL_CALL_PREFIX.length();
        if (formattedPhoneNumber.length() == prefixLength) {
                throw new FormatException(EMPTY_NUMBER_MESSAGE);
        }
    }

}
