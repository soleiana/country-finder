package com.demo.core;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Log
@Component
class BasicValidationRule extends PhoneNumberValidationRule {

    private static final String INVALID_ITU_T_FORMAT = "invalid ITU-T format";
    private static final Pattern ITU_T_PHONE_NUMBER_STRICT = Pattern.compile("^\\+[0-9]{7,15}$");

    @Autowired
    BasicValidationRule(PhoneNumberRegexFactory phoneNumberRegexFactory) {
        super(phoneNumberRegexFactory);
    }

    @Override
    boolean apply(String phoneNumber) {
        return phoneNumberRegexFactory.of(phoneNumber,
                ITU_T_PHONE_NUMBER_STRICT, INVALID_ITU_T_FORMAT)
                .applyWithException();
    }
}
