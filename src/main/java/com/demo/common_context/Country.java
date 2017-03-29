package com.demo.common_context;

import lombok.*;

import java.io.Serializable;

@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Country implements Serializable {

    @NonNull
    private final String name;

    public com.demo.reader_pipeline.communications.Country transform() {
        return com.demo.reader_pipeline.communications.Country.builder()
                .name(name)
                .build();
    }
}
