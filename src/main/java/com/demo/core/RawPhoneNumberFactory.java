package com.demo.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RawPhoneNumberFactory {

    private final FormattedPhoneNumberFactory formattedPhoneNumberFactory;
    private final Formatter formatter;

    @Autowired
    RawPhoneNumberFactory(FormattedPhoneNumberFactory formattedPhoneNumberFactory,
                          Formatter formatter) {
        this.formattedPhoneNumberFactory = formattedPhoneNumberFactory;
        this.formatter = formatter;
    }

    public RawPhoneNumber of(String phoneNumber) {
        return RawPhoneNumber.builder()
                .number(phoneNumber)
                .formattedPhoneNumberFactory(formattedPhoneNumberFactory)
                .formatter(formatter)
                .build();
    }
}
