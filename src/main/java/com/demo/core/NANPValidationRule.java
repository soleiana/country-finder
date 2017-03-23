package com.demo.core;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Log
@Order(10)
@Component
class NANPValidationRule extends FinalValidationRule {

    private static final String NANP_REGEX = "^(?:\\+?1[-. ]?)?\\(?([0-9]{3})\\)?[-. ]?(?:[0-9]{3})[-. ]?(?:[0-9]{4})$";
    private static final Pattern NANP_PHONE_NUMBER = Pattern.compile(NANP_REGEX);


    @Autowired
    NANPValidationRule(Environment environment, PhoneNumberRegexFactory phoneNumberRegexFactory) {
        super(environment, phoneNumberRegexFactory);
    }

     @Override
     boolean apply(String phoneNumber) {
          return phoneNumberRegexFactory.of(phoneNumber, NANP_PHONE_NUMBER, "")
                  .apply();
     }

}
