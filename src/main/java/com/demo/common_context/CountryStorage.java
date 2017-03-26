package com.demo.common_context;

import com.google.common.collect.ImmutableSet;
import lombok.extern.java.Log;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.Set;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Log
@Component
@Scope(SCOPE_SINGLETON)
public class CountryStorage {

    private static final String STORAGE_NAME = "countries";

    private DB db;
    private HTreeMap<CountryCode, Country> countries;

    @PostConstruct
    void init() {
        initializeDB();
        log.info("In-memory database initialized");
        populateDB();
    }

    public void populate() {

    }

    @SuppressWarnings("unchecked")
   public  Set<CountryCode> findAllCountryCodes() {
        return ImmutableSet.copyOf(countries.keySet());
    }

   public Optional<Country> findCountryByCode(CountryCode countryCode) {
        return Optional.ofNullable(countries.get(countryCode));
    }

    private void initializeDB() {
        db = DBMaker.memoryDB()
                .transactionEnable()
                .closeOnJvmShutdown()
                .make();
    }

    @SuppressWarnings("unchecked")
    private void populateDB() {
        countries = (HTreeMap<CountryCode, Country>)db.hashMap(STORAGE_NAME).create();
        addCountries();

        countries.keySet().stream()
                .sorted()
                .forEach(code -> log.info(String.format("country code=%s", code.toString())));

    }

    private void addCountries() {
        CountryCode countryCode1 = new CountryCode("371");
        CountryCode countryCode2 = new CountryCode("370");
        CountryCode countryCode3 = new CountryCode("1");
        CountryCode countryCode4 = new CountryCode("1242");

        countries.put(countryCode1, new Country("Latvia"));
        countries.put(countryCode2, new Country("Lithuania"));
        countries.put(countryCode3, new Country("USA"));
        countries.put(countryCode4, new Country("Bahamas"));
    }

}
