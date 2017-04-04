package com.demo.reader.core;

import com.demo.common.Country;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static org.apache.commons.lang3.StringUtils.remove;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
class ValidatedNumberString extends PhoneNumberString {

    private static final String INTERNATIONAL_CALL_PREFIX = "+";

    @Builder
    ValidatedNumberString(String phoneNumber) {
        super(phoneNumber);
    }

    boolean apply(BasicValidationRule validationRule) {
        return validationRule.apply(phoneNumber);
    }

    Country apply(CountrySearch countrySearch) {
        return countrySearch.apply(phoneNumber);
    }

    ValidatedNumberString withoutInternationalCallPrefix() {
        String numberStringWithoutCallPrefix = remove(phoneNumber, INTERNATIONAL_CALL_PREFIX);
        return of(numberStringWithoutCallPrefix);
    }

    private ValidatedNumberString of(String phoneNumber) {
        return ValidatedNumberString.builder()
                .phoneNumber(phoneNumber)
                .build();
    }
}
