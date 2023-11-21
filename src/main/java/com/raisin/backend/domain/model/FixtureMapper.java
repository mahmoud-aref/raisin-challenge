package com.raisin.backend.domain.model;

import com.raisin.backend.infrastructure.http.client.model.SourceAResponse;
import com.raisin.backend.infrastructure.http.client.model.SourceBResponse;
import org.springframework.stereotype.Component;

// simple mapper no need for ObjectMapper of Mapstruct
@Component
public class FixtureMapper {

    public FixtureObject toFRFromSourceA(SourceAResponse responseA) {
        return FixtureObject.builder().id(responseA.getId()).build();
    }

    public FixtureObject toFRFromSourceB(SourceBResponse responseB) {
        return FixtureObject.builder().id(responseB.getId().getValue()).build();
    }
}
