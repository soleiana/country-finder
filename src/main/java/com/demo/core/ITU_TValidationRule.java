package com.demo.core;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Log
@Order(1)
@Component
class ITU_TValidationRule extends FinalValidationRule {

    private static final Pattern ITU_T_PHONE_NUMBER_LAX = Pattern.compile("^\\+(?:[0-9] ?){6,14}[0-9]$");

    @Autowired
    ITU_TValidationRule(Environment environment, PhoneNumberRegexFactory phoneNumberRegexFactory) {
        super(environment, phoneNumberRegexFactory);
    }

    @Override
    boolean apply(String phoneNumber) {
        return phoneNumberRegexFactory.of(phoneNumber, ITU_T_PHONE_NUMBER_LAX, "")
                .apply();
    }

}
