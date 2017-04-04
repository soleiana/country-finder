package com.demo.reader.core;

import com.demo.common.Country;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
public class ValidatedPhoneNumber {

    @NonNull
    private final ValidatedNumberString numberString;

    @NonNull
    private final CountrySearch countrySearch;

    public Country findCountry() {
        return numberString.withoutInternationalCallPrefix()
                .apply(countrySearch);
    }
}
