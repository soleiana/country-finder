package com.demo.writer_pipeline.communications;

import com.demo.rest.clients.MediaWikiClient;
import com.demo.rest.clients.MediaWikiResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MediaWikiClientAdapter implements RestClientAdapter {

    private final MediaWikiClient mediaWikiClient;

    @Autowired
    public MediaWikiClientAdapter(MediaWikiClient mediaWikiClient) {
        this.mediaWikiClient = mediaWikiClient;
    }

    @Override
    public GetCountriesResponse getListOfCountryCallingCodes() {
        MediaWikiResponseWrapper responseWrapper = mediaWikiClient.getListOfCountryCallingCodes();
        Countries countries = responseWrapper.toCountries();
        return GetCountriesResponse.builder()
                .countries(countries)
                .build();
    }
}
