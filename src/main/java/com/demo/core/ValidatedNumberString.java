package com.demo.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
class ValidatedNumberString extends PhoneNumberString {

    @Builder
    ValidatedNumberString(String phoneNumber) {
        super(phoneNumber);
    }

    boolean apply(BasicValidationRule validationRule) {
        return validationRule.apply(phoneNumber);
    }

    Country build() {
        return Country.builder()
                .name("Latvia")
                .build();
    }

    private ValidatedNumberString of(String phoneNumber) {
        return ValidatedNumberString.builder()
                .phoneNumber(phoneNumber)
                .build();
    }
}
