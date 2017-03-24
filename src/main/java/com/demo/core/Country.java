package com.demo.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.io.Serializable;

@Builder
@ToString
@EqualsAndHashCode
public final class Country implements Serializable {

    @NonNull
    private final String name;

    public com.demo.communications.Country transform() {
        return com.demo.communications.Country.builder()
                .name(name)
                .build();
    }
}
