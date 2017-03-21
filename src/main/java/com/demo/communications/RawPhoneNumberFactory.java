package com.demo.communications;

import com.demo.core.RawPhoneNumber;
import org.springframework.stereotype.Component;

@Component
public class RawPhoneNumberFactory {

    RawPhoneNumber of(String phoneNumber) {
        return RawPhoneNumber.builder()
                .number(phoneNumber)
                .build();
    }
}
