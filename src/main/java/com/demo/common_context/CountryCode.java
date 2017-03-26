package com.demo.common_context;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Builder
@ToString
@EqualsAndHashCode
public final class CountryCode implements Comparable<CountryCode>, Serializable {

    @NonNull
    private final String code;

    @Override
    public int compareTo(@NotNull CountryCode countryCode) {
        return countryCode.length() - this.length();
    }

    public boolean belongsTo(String phoneNumber) {
        return StringUtils.startsWith(phoneNumber, code);
    }

    private int length() {
        return code.length();
    }

}
