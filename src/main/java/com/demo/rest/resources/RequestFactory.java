package com.demo.rest.resources;

import com.demo.reader_pipeline.communications.SearchEngine;
import com.demo.reader_pipeline.core.RawPhoneNumberFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class RequestFactory {

    private final RawPhoneNumberFactory rawPhoneNumberFactory;

    @Autowired
    public RequestFactory(RawPhoneNumberFactory rawPhoneNumberFactory) {
        this.rawPhoneNumberFactory = rawPhoneNumberFactory;
    }

    SearchEngine of(String phoneNumber) {
        return SearchEngine.builder()
                .phoneNumber(phoneNumber)
                .rawPhoneNumberFactory(rawPhoneNumberFactory)
                .build();
    }
}
