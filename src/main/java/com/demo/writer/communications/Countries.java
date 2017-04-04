package com.demo.writer.communications;

import com.demo.writer.core.RawCountryString;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
public class Countries {

    @NonNull
    private final String countries;

    public RawCountryString toRawString() {
        return RawCountryString.builder()
                .countries(countries)
                .build();
    }
}
