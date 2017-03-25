package com.demo.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Builder
@ToString
@EqualsAndHashCode
final class CountryCode implements Comparable<CountryCode>, Serializable {

    @NonNull
    private final String code;

    @Override
    public int compareTo(@NotNull CountryCode countryCode) {
        return countryCode.length() - this.length();
    }

    private int length() {
        return code.length();
    }

}
