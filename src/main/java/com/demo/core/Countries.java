package com.demo.core;

import lombok.extern.java.Log;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Log
@Component
@Scope(SCOPE_SINGLETON)
class Countries {

    private DB db;
    private HTreeMap<String, Country> countries;

    @PostConstruct
    void populate() {
        initializeDB();
        log.info("In-memory database initialized");
        populateDB();
    }

    private void initializeDB() {
        db = DBMaker.memoryDB()
                .transactionEnable()
                .closeOnJvmShutdown()
                .make();
    }

    @SuppressWarnings("unchecked")
    private void populateDB() {
        countries = (HTreeMap<String, Country>)db.hashMap("countries").create();
        countries.put("371", new Country("Latvia"));
        log.info(String.format("country code=371, country=%s", countries.get("371").toString()));

    }
}
