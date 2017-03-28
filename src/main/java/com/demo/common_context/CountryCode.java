package com.demo.common_context;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import static org.apache.commons.lang3.StringUtils.startsWith;

@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class CountryCode implements Comparable<CountryCode>, Serializable {

    @NonNull
    private final String code;

    @Override
    public int compareTo(@NotNull CountryCode countryCode) {
        return countryCode.length() - this.length();
    }

    public boolean belongsTo(String phoneNumber) {
        return startsWith(phoneNumber, code);
    }

    private int length() {
        return code.length();
    }

}
