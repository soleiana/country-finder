package com.demo.reader_pipeline.core;

abstract class FinalValidationRule extends PhoneNumberValidationRule {

    public FinalValidationRule(PhoneNumberRegexFactory phoneNumberRegexFactory) {
        super(phoneNumberRegexFactory);
    }
}
