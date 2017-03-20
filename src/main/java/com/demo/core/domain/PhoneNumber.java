package com.demo.core.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhoneNumber {
    private String number;
}
