package com.demo.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class RawPhoneNumber extends PhoneNumber {

    private final PhoneNumberFormatter phoneNumberFormatter;
    private final FormattedPhoneNumberFactory formattedPhoneNumberFactory;

    @Builder
     RawPhoneNumber(String number,
                          FormattedPhoneNumberFactory formattedPhoneNumberFactory,
                          PhoneNumberFormatter phoneNumberFormatter) {
        super(number);
        this.formattedPhoneNumberFactory = formattedPhoneNumberFactory;
        this.phoneNumberFormatter = phoneNumberFormatter;
    }

    public FormattedPhoneNumber format() {
        String normalizedNumberWithLeadingPlus = phoneNumberFormatter.apply(number);
        return formattedPhoneNumberFactory.of(normalizedNumberWithLeadingPlus);
    }
}
