package com.demo.core;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(10)
@Component
class NAMPValidationRule implements ValidationRule {

     @Override
     public void apply(String phoneNumber) {
     }
}
