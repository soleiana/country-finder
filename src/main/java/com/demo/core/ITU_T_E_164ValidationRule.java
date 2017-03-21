package com.demo.core;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(20)
@Component
class ITU_T_E_164ValidationRule implements ValidationRule {

    @Override
    public void apply(String phoneNumber) {
    }
}
