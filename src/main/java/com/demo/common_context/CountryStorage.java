package com.demo.common_context;

import com.google.common.collect.ImmutableSet;
import lombok.extern.java.Log;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
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

    @SuppressWarnings("unchecked")
    public void save(Map<CountryCode, Country> countryMap) {
        countries = (HTreeMap<CountryCode, Country>)db.hashMap(STORAGE_NAME).createOrOpen();

        countryMap.forEach(
                (key, value) -> countries.put(key, value)
        );
        db.commit();
        log.info("List of countries serialized");
        printCountries();
    }

    @SuppressWarnings("unchecked")
    public  Set<CountryCode> findAllCountryCodes() {
        return ImmutableSet.copyOf(countries.keySet());
    }

    public Optional<Country> findCountryByCode(CountryCode countryCode) {
        return Optional.ofNullable(countries.get(countryCode));
    }

    @PostConstruct
    void init() {
        initializeDB();
        log.info("In-memory database initialized");
        populateDBWithTestData();
    }

    private void initializeDB() {
        db = DBMaker.memoryDB()
                .transactionEnable()
                .closeOnJvmShutdown()
                .make();
    }

    @SuppressWarnings("unchecked")
    private void populateDBWithTestData() {
        countries = (HTreeMap<CountryCode, Country>)db.hashMap(STORAGE_NAME).create();
        addTestCountries();
        printCountries();
    }

    @SuppressWarnings("unchecked")
    private void printCountries() {
        countries.forEach((key, value) ->
                log.info(String.format("code=%s, country=%s", key.toString(), value.toString())));

    }

    private void addTestCountries() {
        CountryCode countryCode1 = new CountryCode("071");
        CountryCode countryCode2 = new CountryCode("070");
        countries.put(countryCode1, new Country("Serendipity"));
        countries.put(countryCode2, new Country("Oz"));
    }

}
