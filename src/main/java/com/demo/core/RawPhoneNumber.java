package com.demo.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class RawPhoneNumber extends PhoneNumber {

    private final FormattingOperations formattingOperations;
    private final FormattedPhoneNumberFactory formattedPhoneNumberFactory;

    @Builder
     RawPhoneNumber(String number,
                    FormattedPhoneNumberFactory formattedPhoneNumberFactory,
                    FormattingOperations formattingOperations) {
        super(number);
        this.formattedPhoneNumberFactory = formattedPhoneNumberFactory;
        this.formattingOperations = formattingOperations;
    }

    public FormattedPhoneNumber format() {
        String formattedNumber = formattingOperations.apply(number);
        return formattedPhoneNumberFactory.of(formattedNumber);
    }
}
