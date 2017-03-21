package com.demo.core;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Country {

    @NonNull
    private String name;

    public com.demo.communications.Country transform() {
        return com.demo.communications.Country.builder()
                .name(name)
                .build();
    }
}
