package com.demo.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class RawPhoneNumber extends PhoneNumber {

    private final Formatter formatter;
    private final FormattedPhoneNumberFactory formattedPhoneNumberFactory;

    @Builder
     RawPhoneNumber(String number,
                    FormattedPhoneNumberFactory formattedPhoneNumberFactory,
                    Formatter formatter) {
        super(number);
        this.formattedPhoneNumberFactory = formattedPhoneNumberFactory;
        this.formatter = formatter;
    }

    public FormattedPhoneNumber format() {
        String normalizedNumberWithLeadingPlus = formatter.apply(number);
        return formattedPhoneNumberFactory.of(normalizedNumberWithLeadingPlus);
    }
}
