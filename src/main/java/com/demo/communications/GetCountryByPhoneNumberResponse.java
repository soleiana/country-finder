package com.demo.communications;

import com.demo.communications.domain.Country;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetCountryByPhoneNumberResponse {
    private Country country;
}
