package com.demo.rest.resources;

import com.demo.communications.GetCountryByPhoneNumberRequest;
import com.demo.communications.RawPhoneNumberFactory;
import org.springframework.stereotype.Component;

@Component
class RequestFactory {

    private final RawPhoneNumberFactory rawPhoneNumberFactory;

    public RequestFactory(RawPhoneNumberFactory rawPhoneNumberFactory) {
        this.rawPhoneNumberFactory = rawPhoneNumberFactory;
    }

    GetCountryByPhoneNumberRequest of(String phoneNumber) {
        return GetCountryByPhoneNumberRequest.builder()
                .phoneNumber(phoneNumber)
                .rawPhoneNumberFactory(rawPhoneNumberFactory)
                .build();
    }
}
