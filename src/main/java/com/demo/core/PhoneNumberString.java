package com.demo.core;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
abstract class PhoneNumberString {

    @NonNull
    final String phoneNumber;

    String removeCharacters(String phoneNumber, Pattern charactersToRemove) {
        return charactersToRemove.matcher(phoneNumber)
                .replaceAll(EMPTY);
    }

}
