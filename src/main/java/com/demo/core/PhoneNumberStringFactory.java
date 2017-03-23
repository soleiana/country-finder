package com.demo.core;

import org.springframework.stereotype.Component;

@Component
class PhoneNumberStringFactory {

    PhoneNumberString of(String phoneNumber) {
        return PhoneNumberString.builder()
                .phoneNumber(phoneNumber)
                .build();
    }
}
