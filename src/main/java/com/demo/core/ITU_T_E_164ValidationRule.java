package com.demo.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Order(20)
@Component
class ITU_T_E_164ValidationRule extends PhoneNumberValidationRule {

    @Autowired
    ITU_T_E_164ValidationRule(Environment environment, PhoneNumberRegexFactory phoneNumberRegexFactory) {
        super(environment, phoneNumberRegexFactory);
    }

    @Override
    public boolean apply(String phoneNumber) {
        return true;
    }
}
