package com.demo.reader.core;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Log
@Order(30)
@Component
class EPPValidationRule extends FinalValidationRule {

    private static final String EPP_REGEX = "^\\+[0-9]{1,3}\\.[0-9]{4,14}(?:[x|X].+)?$";
    private static final Pattern EPP_PHONE_NUMBER = Pattern.compile(EPP_REGEX);

    @Autowired
    EPPValidationRule(PhoneNumberRegexFactory phoneNumberRegexFactory) {
        super(phoneNumberRegexFactory);
    }

    @Override
    boolean apply(String phoneNumber) {
        return phoneNumberRegexFactory.of(phoneNumber, EPP_PHONE_NUMBER, "")
                .apply();
    }
}
