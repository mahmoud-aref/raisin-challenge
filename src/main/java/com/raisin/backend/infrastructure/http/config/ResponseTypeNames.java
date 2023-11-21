package com.raisin.backend.infrastructure.http.config;

public interface ResponseTypeNames {
    // this workaround to handle the optional response is not ideal
    // using reflection to get the type of the optional response
    // is used by the FeignConfiguration class and the FixtureDecoder class
    String OPTIONAL_SOURCE_A_RESPONSE = "java.util.Optional<com.raisin.backend.infrastructure.http.client.model.SourceAResponse>";
    String OPTIONAL_SOURCE_B_RESPONSE = "java.util.Optional<com.raisin.backend.infrastructure.http.client.model.SourceBResponse>";
    String OPTIONAL_REPORT_RESPONSE = "java.util.Optional<com.raisin.backend.infrastructure.http.client.model.ReportResponse>";
}
