package com.demo.rest.resources;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Country {

    @NonNull
    private final String name;
}
