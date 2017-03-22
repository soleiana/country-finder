package com.demo.core;

import org.springframework.core.env.Environment;

abstract class PhoneNumberValidationRule {

    final Environment environment;
    final PhoneNumberRegexFactory phoneNumberRegexFactory;

    PhoneNumberValidationRule(Environment environment, PhoneNumberRegexFactory phoneNumberRegexFactory) {
        this.environment = environment;
        this.phoneNumberRegexFactory = phoneNumberRegexFactory;
    }

    abstract boolean apply(String phoneNumber);
}
