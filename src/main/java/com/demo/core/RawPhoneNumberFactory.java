package com.demo.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RawPhoneNumberFactory {

    private final FormattedPhoneNumberFactory formattedPhoneNumberFactory;
    private final FormattingOperations formattingOperations;

    @Autowired
    RawPhoneNumberFactory(FormattedPhoneNumberFactory formattedPhoneNumberFactory,
                          FormattingOperations formattingOperations) {
        this.formattedPhoneNumberFactory = formattedPhoneNumberFactory;
        this.formattingOperations = formattingOperations;
    }

    public RawPhoneNumber of(String phoneNumber) {
        return RawPhoneNumber.builder()
                .number(phoneNumber)
                .formattedPhoneNumberFactory(formattedPhoneNumberFactory)
                .formattingOperations(formattingOperations)
                .build();
    }
}
