package com.demo.core;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
abstract class PhoneNumber {

    @NonNull
    final PhoneNumberString numberString;
}
