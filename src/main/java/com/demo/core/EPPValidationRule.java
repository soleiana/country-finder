package com.demo.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Order(30)
@Component
class EPPValidationRule extends PhoneNumberValidationRule {

    @Autowired
    EPPValidationRule(Environment environment, PhoneNumberRegexFactory phoneNumberRegexFactory) {
        super(environment, phoneNumberRegexFactory);
    }

    @Override
    boolean apply(String phoneNumber) {
        return true;
    }
}
