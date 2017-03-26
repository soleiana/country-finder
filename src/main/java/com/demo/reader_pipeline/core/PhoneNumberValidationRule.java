package com.demo.reader_pipeline.core;

abstract class PhoneNumberValidationRule {

    final PhoneNumberRegexFactory phoneNumberRegexFactory;

    PhoneNumberValidationRule(PhoneNumberRegexFactory phoneNumberRegexFactory) {
        this.phoneNumberRegexFactory = phoneNumberRegexFactory;
    }

    abstract boolean apply(String phoneNumber);
}
