package com.demo.rest.clients;

import com.demo.writer_pipeline.exceptions.WebPageAccessException;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Log
@Component
public class MediaWikiClient {

    private static final String REQUEST_URL =
            "https://en.wikipedia.org/w/api.php?action=parse&pageid=5770&prop=wikitext&section=2&format=json";

    private static final String RESOURCE_UNAVAILABLE = "requested resource unavailable";


    public MediaWikiResponseWrapper getListOfCountryCallingCodes() {
        RestTemplate rest = new RestTemplate();
        ResponseEntity<MediaWikiResponseWrapper> response = rest.getForEntity(REQUEST_URL, MediaWikiResponseWrapper.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            log.info(RESOURCE_UNAVAILABLE);
            throw new WebPageAccessException(RESOURCE_UNAVAILABLE);
        }
        return response.getBody();
    }

}
