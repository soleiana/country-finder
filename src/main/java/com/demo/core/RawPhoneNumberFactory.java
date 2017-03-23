package com.demo.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RawPhoneNumberFactory extends PhoneNumberFactory {

    private final FormattedPhoneNumberFactory formattedPhoneNumberFactory;

    @Autowired
    RawPhoneNumberFactory(FormattedPhoneNumberFactory formattedPhoneNumberFactory,
                          PhoneNumberStringFactory phoneNumberStringFactory) {
        super(phoneNumberStringFactory);
        this.formattedPhoneNumberFactory = formattedPhoneNumberFactory;
    }

    public RawPhoneNumber of(String phoneNumber) {
        PhoneNumberString numberString = phoneNumberStringFactory.of(phoneNumber);
        return RawPhoneNumber.builder()
                .numberString(numberString)
                .formattedPhoneNumberFactory(formattedPhoneNumberFactory)
                .build();
    }
}
