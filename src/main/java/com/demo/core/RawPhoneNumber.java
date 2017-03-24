package com.demo.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public final class RawPhoneNumber extends PhoneNumber {

    private final FormattedPhoneNumberFactory formattedPhoneNumberFactory;

    @Builder
    RawPhoneNumber(PhoneNumberString numberString,
                    FormattedPhoneNumberFactory formattedPhoneNumberFactory){

        super(numberString);
        this.formattedPhoneNumberFactory = formattedPhoneNumberFactory;
    }

    public FormattedPhoneNumber format() {
        PhoneNumberString numberStringAsNonSpaceCharacters = numberString
                .withoutSpaceCharacters()
                .withInternationalCallPrefix()
                .checkFormat();

        return formattedPhoneNumberFactory.of(numberStringAsNonSpaceCharacters);
    }
}
