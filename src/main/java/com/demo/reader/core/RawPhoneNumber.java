package com.demo.reader.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
public class RawPhoneNumber {

    @NonNull
    private final RawNumberString numberString;

    @NonNull
    private final FormattedPhoneNumberFactory formattedPhoneNumberFactory;

    public FormattedPhoneNumber format() {
        FormattedNumberString formattedNumberString = numberString.withoutSpaceCharacters()
                .withInternationalCallPrefix()
                .build();

        return formattedPhoneNumberFactory.of(formattedNumberString);
    }
}
