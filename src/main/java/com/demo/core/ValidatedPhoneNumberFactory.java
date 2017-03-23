package com.demo.core;

import org.springframework.stereotype.Component;

@Component
class ValidatedPhoneNumberFactory {

    ValidatedPhoneNumber of(String phoneNumber) {
        return ValidatedPhoneNumber.builder()
                .number(phoneNumber)
                .build();
    }
}
