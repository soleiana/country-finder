package com.demo.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
public final class Country {

    @NonNull
    private final String name;

    public com.demo.communications.Country transform() {
        return com.demo.communications.Country.builder()
                .name(name)
                .build();
    }
}
