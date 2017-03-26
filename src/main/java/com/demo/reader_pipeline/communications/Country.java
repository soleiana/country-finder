package com.demo.reader_pipeline.communications;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
public class Country {

    @NonNull
    private final String name;

    public com.demo.rest.resources.Country transform() {
        return com.demo.rest.resources.Country.builder()
                .name(name)
                .build();
    }
}
