package com.demo.writer_pipeline.core;

import com.demo.common_context.CountryStorage;
import com.demo.writer_pipeline.communications.GetCountriesResponse;
import com.demo.writer_pipeline.communications.RestClientAdapter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Log
@Scope(SCOPE_SINGLETON)
@Component("writer")
class CountryRegister {

    private final RestClientAdapter restClientAdapter;
    private final CountryStorage storage;

    @Autowired
    public CountryRegister(CountryStorage storage, RestClientAdapter restClientAdapter) {
        this.storage = storage;
        this.restClientAdapter = restClientAdapter;
    }

    @PostConstruct
    void bootstrap() {
        GetCountriesResponse response = restClientAdapter.getListOfCountryCallingCodes();
        log.info(response.toString());
    }
}
