package com.demo.reader_pipeline.core;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
abstract class PhoneNumberString {

    @NonNull
    final String phoneNumber;

}
