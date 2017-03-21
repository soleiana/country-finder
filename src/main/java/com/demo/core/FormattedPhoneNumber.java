package com.demo.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class FormattedPhoneNumber extends PhoneNumber {

    @Builder
    public FormattedPhoneNumber(String number) {
        super(number);
    }

    public ValidatedPhoneNumber validate() {
        return ValidatedPhoneNumber.builder()
                .number(number)
                .build();
    }
}
