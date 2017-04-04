package com.demo.rest.clients;

import com.demo.writer.communications.Countries;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MediaWikiResponseWrapper {

    @JsonProperty("parse")
    private MediaWikiResponse mediaWikiResponse;


    public Countries toCountries() {
        return mediaWikiResponse.toCountries();
    }

}
