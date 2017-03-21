package com.demo.communications;

import com.demo.core.RawPhoneNumberFactory;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
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
