package com.demo.core;

import org.springframework.core.env.Environment;

abstract class FinalValidationRule extends PhoneNumberValidationRule {

    public FinalValidationRule(Environment environment, PhoneNumberRegexFactory phoneNumberRegexFactory) {
        super(environment, phoneNumberRegexFactory);
    }
}
