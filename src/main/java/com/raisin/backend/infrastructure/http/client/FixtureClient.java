package com.raisin.backend.infrastructure.http.client;

import com.raisin.backend.domain.model.FixtureObject;
import com.raisin.backend.infrastructure.http.client.model.ReportResponse;
import com.raisin.backend.infrastructure.http.client.model.SourceAResponse;
import com.raisin.backend.infrastructure.http.client.model.SourceBResponse;
import com.raisin.backend.infrastructure.http.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(
        name = "fixture-api",
        url = "${internal-api.fixture-api-base-url}",
        configuration = FeignConfiguration.class)
public interface FixtureClient {
    // I am using Optional, but we can use ResponseEntity as well
    @GetMapping(path = "/source/a", consumes = "application/json")
    Optional<SourceAResponse> getFixtureFromSourceA();

    @GetMapping(path = "/source/b", consumes = "application/xml")
    Optional<SourceBResponse> getFixtureFromSourceB();

    @PostMapping(path = "/sink/a", produces = "application/json")
    Optional<ReportResponse> reportFixture(@RequestBody FixtureObject fixtureObject);

}
