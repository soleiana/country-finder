package com.demo.core;

import com.demo.exceptions.CountrySearchException;
import lombok.extern.java.Log;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.Set;

import static java.util.Optional.ofNullable;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Log
@Component
@Scope(SCOPE_SINGLETON)
class Countries {

    private static final String COUNTRY_CODE_NOT_FOUND = "country code not found";
    private static final String COUNTRY_NOT_FOUND = "country not found";

    private DB db;
    private HTreeMap<CountryCode, Country> countries;

    @PostConstruct
    void populate() {
        initializeDB();
        log.info("In-memory database initialized");
        populateDB();
    }

    Country searchByPhoneNumber(String phoneNumber) {
        CountryCode countryCode = searchCountryCode(phoneNumber)
                .orElseThrow(()-> new CountrySearchException(COUNTRY_CODE_NOT_FOUND));

        return ofNullable(countries.get(countryCode))
                .orElseThrow(() -> new CountrySearchException(COUNTRY_NOT_FOUND));
    }

    @SuppressWarnings("unchecked")
    private Optional<CountryCode> searchCountryCode(String phoneNumber) {
        Set<CountryCode> countryCodes =  countries.keySet();
        return countryCodes.stream()
                .sorted()
                .filter(code -> code.belongsTo(phoneNumber))
                .findFirst();
    }

    private void initializeDB() {
        db = DBMaker.memoryDB()
                .transactionEnable()
                .closeOnJvmShutdown()
                .make();
    }

    @SuppressWarnings("unchecked")
    private void populateDB() {
        countries = (HTreeMap<CountryCode, Country>)db.hashMap("countries").create();
        CountryCode countryCode1 = new CountryCode("371");
        CountryCode countryCode2 = new CountryCode("370");
        CountryCode countryCode3 = new CountryCode("1");
        CountryCode countryCode4 = new CountryCode("1242");

        countries.put(countryCode1, new Country("Latvia"));
        countries.put(countryCode2, new Country("Lithuania"));

        countries.put(countryCode3, new Country("USA"));
        countries.put(countryCode4, new Country("Bahamas"));
        countries.keySet().stream()
                .sorted()
                .forEach(code -> log.info(String.format("country code=%s", code.toString())));

    }
}
