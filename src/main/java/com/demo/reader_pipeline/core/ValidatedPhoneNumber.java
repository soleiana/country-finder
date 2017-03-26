package com.demo.reader_pipeline.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
public final class ValidatedPhoneNumber {

    private final ValidatedNumberString numberString;
    private final CountrySearch countrySearch;

    public Country findCountry() {
        return numberString.withoutInternationalCallPrefix()
                .apply(countrySearch);
    }
}
