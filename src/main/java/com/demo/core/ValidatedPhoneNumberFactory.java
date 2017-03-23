package com.demo.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class ValidatedPhoneNumberFactory extends PhoneNumberFactory {

    @Autowired
    public ValidatedPhoneNumberFactory(PhoneNumberStringFactory phoneNumberStringFactory) {
        super(phoneNumberStringFactory);
    }

    ValidatedPhoneNumber of(String phoneNumber) {
        PhoneNumberString numberString = phoneNumberStringFactory.of(phoneNumber);

        return ValidatedPhoneNumber.builder()
                .numberString(numberString)
                .build();
    }
}
