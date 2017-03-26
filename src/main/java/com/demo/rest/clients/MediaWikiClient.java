package com.demo.rest.clients;

import com.demo.writer_pipeline.exceptions.WebPageAccessException;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpStatus.OK;

@Log
@Component
public class MediaWikiClient {

    private static final String REQUEST_URL =
            "https://en.wikipedia.org/w/api.php?action=parse&pageid=5770&prop=wikitext&section=2&format=json";

    private static final String RESOURCE_UNAVAILABLE = "requested Wiki resource unavailable";


    public MediaWikiResponseWrapper getListOfCountryCallingCodes() {
        ResponseEntity<MediaWikiResponseWrapper> response;
        try {
            response = getWikiResponse();
        } catch (RuntimeException exception) {
            log.info(exception.getMessage());
            exception.printStackTrace();
            throw new WebPageAccessException(RESOURCE_UNAVAILABLE);
        }
        return response.getBody();
    }

    private ResponseEntity<MediaWikiResponseWrapper> getWikiResponse() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<MediaWikiResponseWrapper> response = restTemplate.getForEntity(REQUEST_URL, MediaWikiResponseWrapper.class);

        if (response.getStatusCode() != OK) {
            log.info(RESOURCE_UNAVAILABLE);
            throw new WebPageAccessException(RESOURCE_UNAVAILABLE);
        }
        return response;
    }

}
