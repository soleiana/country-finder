package com.demo.core;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(30)
@Component
class EPPValidationRule implements ValidationRule {

    @Override
    public void apply(String phoneNumber) {
    }
}
