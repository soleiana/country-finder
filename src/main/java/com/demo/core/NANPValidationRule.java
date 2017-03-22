package com.demo.core;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Order(10)
@Component
class NANPValidationRule implements ValidationRule {

     private static final String NANP_REGEX = "^(?:\\+?1[-. ]?)?\\(?([0-9]{3})\\)?[-. ]?(?:[0-9]{3})[-. ]?(?:[0-9]{4})$";
     private static final Pattern NANP_PHONE_NUMBER = Pattern.compile(NANP_REGEX);

     @Override
     public boolean apply(String phoneNumber) {
          return true;
     }
}
