package com.demo.rest.resources;

import com.demo.reader.communications.SearchEngine;
import com.demo.reader.core.RawPhoneNumberFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class SearchEngineFactory {

    private final RawPhoneNumberFactory rawPhoneNumberFactory;

    @Autowired
    public SearchEngineFactory(RawPhoneNumberFactory rawPhoneNumberFactory) {
        this.rawPhoneNumberFactory = rawPhoneNumberFactory;
    }

    SearchEngine of(String phoneNumber) {
        return SearchEngine.builder()
                .phoneNumber(phoneNumber)
                .rawPhoneNumberFactory(rawPhoneNumberFactory)
                .build();
    }
}
