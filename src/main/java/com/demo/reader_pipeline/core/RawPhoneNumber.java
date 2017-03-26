package com.demo.reader_pipeline.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
public final class RawPhoneNumber {

    private final RawNumberString numberString;
    private final FormattedPhoneNumberFactory formattedPhoneNumberFactory;

    public FormattedPhoneNumber format() {
        FormattedNumberString formattedNumberString = numberString.withoutSpaceCharacters()
                .withInternationalCallPrefix()
                .checkFormat()
                .build();

        return formattedPhoneNumberFactory.of(formattedNumberString);
    }
}
