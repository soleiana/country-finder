package com.demo.rest.resources;

import lombok.*;

@Builder
@ToString
@EqualsAndHashCode
public class Country {

    @NonNull
    @Getter
    private final String name;

}
