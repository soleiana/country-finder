package com.demo.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class RawPhoneNumber extends PhoneNumber {

    private final FormattedPhoneNumberFactory formattedPhoneNumberFactory;

    @Builder
    RawPhoneNumber(PhoneNumberString numberString,
                    FormattedPhoneNumberFactory formattedPhoneNumberFactory){

        super(numberString);
        this.formattedPhoneNumberFactory = formattedPhoneNumberFactory;
    }

    public FormattedPhoneNumber format() {
        String formattedNumber = numberString.formatForFinalValidation();
        return formattedPhoneNumberFactory.of(formattedNumber);
    }
}
