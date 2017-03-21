package com.demo.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class RawPhoneNumber extends PhoneNumber {

    @Builder
    public RawPhoneNumber(String number) {
        super(number);
    }

    public FormattedPhoneNumber format() {
        String formattedNumber = number;
        return FormattedPhoneNumber.builder()
                .number(formattedNumber)
                .build();
    }
}
