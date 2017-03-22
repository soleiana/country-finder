package com.demo.core;

import org.springframework.core.env.Environment;

abstract class PhoneNumberOperations {

    final Environment environment;

    public PhoneNumberOperations(Environment environment) {
        this.environment = environment;
    }

    abstract String apply(String phoneNumber);
}
