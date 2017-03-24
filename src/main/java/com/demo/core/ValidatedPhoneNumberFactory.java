package com.demo.core;

import org.springframework.stereotype.Component;

@Component
class ValidatedPhoneNumberFactory {

    ValidatedPhoneNumber of(PhoneNumberString numberString) {
        return ValidatedPhoneNumber.builder()
                .numberString(numberString)
                .build();
    }
}
