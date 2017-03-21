package com.demo.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RawPhoneNumberFactory {

    private final FormattedPhoneNumberFactory formattedPhoneNumberFactory;
    private final PhoneNumberFormatter phoneNumberFormatter;

    @Autowired
    RawPhoneNumberFactory(FormattedPhoneNumberFactory formattedPhoneNumberFactory,
                                 PhoneNumberFormatter phoneNumberFormatter) {
        this.formattedPhoneNumberFactory = formattedPhoneNumberFactory;
        this.phoneNumberFormatter = phoneNumberFormatter;
    }

    public RawPhoneNumber of(String phoneNumber) {
        return RawPhoneNumber.builder()
                .number(phoneNumber)
                .formattedPhoneNumberFactory(formattedPhoneNumberFactory)
                .phoneNumberFormatter(phoneNumberFormatter)
                .build();
    }
}
