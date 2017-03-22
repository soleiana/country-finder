package com.demo.core;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Log
@Component
class PreValidationOperations extends PhoneNumberOperations {

    @Autowired
    PreValidationOperations(Environment environment) {
        super(environment);
    }

    @Override
    String apply(String phoneNumber) {
        return "";
    }
}
