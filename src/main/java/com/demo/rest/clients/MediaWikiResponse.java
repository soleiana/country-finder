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
@AllArgsConstructor
@EqualsAndHashCode
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class MediaWikiResponse {

    private String title;

    @JsonProperty("pageid")
    private int pageId;

    @JsonProperty("wikitext")
    private WikiCountries wikiCountries;


    Countries toCountries() {
        return wikiCountries.toCountries();
    }

}
