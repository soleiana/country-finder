package com.demo.common;

import lombok.*;

import java.io.Serializable;

@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Country implements Serializable {

    @NonNull
    private final String name;

    public com.demo.reader.communications.Country transform() {
        return com.demo.reader.communications.Country.builder()
                .name(name)
                .build();
    }
}
