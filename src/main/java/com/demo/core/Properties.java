package com.demo.core;

enum Properties {

    MIN_DIGITS_IN_USE_PROPERTY("phone.number.min_digits_in_use"),
    MAX_DIGITS_ITU_T_PROPERTY("phone.number.max_digits_itu_t"),
    EMPTY_NUMBER_MESSAGE_PROPERTY("phone.number.empty_number_message"),
    EXTENSION_DELIMITER_PROPERTY("phone.number.extension_delimiter"),
    INTERNATIONAL_CALL_PREFIX_PROPERTY("phone.number.international_call_prefix")
    ;

    Properties(String alias) {
        this.alias = alias;
    }

    private String alias;

    String asAlias() {
        return alias;
    }
}
