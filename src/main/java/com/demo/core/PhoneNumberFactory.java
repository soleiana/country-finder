package com.demo.core;

abstract class PhoneNumberFactory {

    final PhoneNumberStringFactory phoneNumberStringFactory;

    PhoneNumberFactory(PhoneNumberStringFactory phoneNumberStringFactory) {
        this.phoneNumberStringFactory = phoneNumberStringFactory;
    }
}
