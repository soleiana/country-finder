package com.demo.writer_pipeline.communications;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
public class GetCountriesResponse {

    @NonNull
    private final Countries countries;
}
