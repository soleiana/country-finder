package com.demo.communications;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetCountryByPhoneNumberRequest {
    private String phoneNumber;
}
