package com.demo.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
final class CountryCode {

    @NonNull
    private final String code;

}
