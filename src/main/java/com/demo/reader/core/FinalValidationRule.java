package com.demo.reader.core;

abstract class FinalValidationRule extends PhoneNumberValidationRule {

    public FinalValidationRule(PhoneNumberRegexFactory phoneNumberRegexFactory) {
        super(phoneNumberRegexFactory);
    }
}
