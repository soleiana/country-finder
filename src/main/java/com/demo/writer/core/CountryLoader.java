package com.demo.writer.core;

import com.demo.writer.communications.Countries;
import com.demo.writer.communications.RestClientAdapter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Log
@Scope(SCOPE_SINGLETON)
@Component
class CountryLoader {

    private final RestClientAdapter restClientAdapter;
    private final RawCountryFactory rawCountryFactory;

    @Autowired
    CountryLoader(RestClientAdapter restClientAdapter, RawCountryFactory rawCountryFactory) {
        this.restClientAdapter = restClientAdapter;
        this.rawCountryFactory = rawCountryFactory;
    }

    @PostConstruct
    void bootstrap() {
        Countries countries = restClientAdapter.getListOfCountryCallingCodes();

        log.info("List of countries fetched...");
        log.info(countries.toString());

        rawCountryFactory.of(countries)
                .tokenize()
                .parse()
                .serialize();

        log.info("List of countries loaded");
    }
}
