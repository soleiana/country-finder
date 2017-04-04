package com.demo.rest.clients;

import com.demo.writer.communications.Countries;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class WikiCountries {

    @JsonProperty("*")
    private String countries;

    Countries toCountries() {
        return Countries.builder()
                .countries(countries)
                .build();
    }
}
