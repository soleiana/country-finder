package com.demo.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
abstract class PhoneNumber {

    @NonNull
    protected final String number;
}
