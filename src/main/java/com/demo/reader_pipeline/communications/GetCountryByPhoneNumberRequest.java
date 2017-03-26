package com.demo.reader_pipeline.communications;

import com.demo.reader_pipeline.core.RawPhoneNumberFactory;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
public class GetCountryByPhoneNumberRequest {

    @NonNull
    private final String phoneNumber;

    private final RawPhoneNumberFactory rawPhoneNumberFactory;

    public GetCountryByPhoneNumberResponse execute() {
        return rawPhoneNumberFactory.of(phoneNumber)
                .format()
                .validate()
                .findCountry()
                .transform()
                .toResponse();
    }
}