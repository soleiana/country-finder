package com.demo.reader_pipeline.core;

import com.demo.common_context.Country;
import com.demo.common_context.CountryCode;
import com.demo.common_context.CountryStorage;
import com.demo.reader_pipeline.exceptions.SearchException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Log
@Component("reader")
class CountryRegister {

    private static final String COUNTRY_CODE_NOT_FOUND = "country code not found";
    private static final String COUNTRY_NOT_FOUND = "country not found";

    private final CountryStorage storage;

    @Autowired
    CountryRegister(CountryStorage storage) {
        this.storage = storage;
    }

    Country searchCountry(String phoneNumber) {
        CountryCode countryCode = searchCountryCode(phoneNumber)
                .orElseThrow(()-> new SearchException(COUNTRY_CODE_NOT_FOUND));

        return storage.findCountryByCode(countryCode)
                .orElseThrow(() -> new SearchException(COUNTRY_NOT_FOUND));
    }

    private Optional<CountryCode> searchCountryCode(String phoneNumber) {
        return storage.findAllCountryCodes().stream()
                .sorted()
                .filter(code -> code.belongsTo(phoneNumber))
                .findFirst();
    }

}
