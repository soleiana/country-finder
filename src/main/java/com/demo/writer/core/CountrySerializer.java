package com.demo.writer.core;

import com.demo.common.Country;
import com.demo.common.CountryCode;
import com.demo.common.CountryStorage;
import com.demo.writer.exceptions.StorageAccessException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Log
@Component
class CountrySerializer {

    private static final String SERIALIZATION_ERROR = "unable to serialize countries";
    private final CountryStorage storage;

    @Autowired
    CountrySerializer(CountryStorage storage) {
        this.storage = storage;
    }

    void apply(Map<CountryCode, Country> countryMap) {
        try {
            storage.save(countryMap);
            log.info("Countries loaded, open champagne!");

        } catch (RuntimeException exception) {
            log.info(SERIALIZATION_ERROR);
            throw new StorageAccessException(SERIALIZATION_ERROR);
        }
    }

}
