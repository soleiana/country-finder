package com.demo.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RawPhoneNumberFactory {

    private final FormattedPhoneNumberFactory formattedPhoneNumberFactory;

    @Autowired
    RawPhoneNumberFactory(FormattedPhoneNumberFactory formattedPhoneNumberFactory) {
        this.formattedPhoneNumberFactory = formattedPhoneNumberFactory;
    }

    public RawPhoneNumber of(String phoneNumber) {
        PhoneNumberString numberString = ofPhoneNumber(phoneNumber);
        return RawPhoneNumber.builder()
                .numberString(numberString)
                .formattedPhoneNumberFactory(formattedPhoneNumberFactory)
                .build();
    }

    private PhoneNumberString ofPhoneNumber(String phoneNumber) {
        return PhoneNumberString.builder()
                .phoneNumber(phoneNumber)
                .build();
    }
}
