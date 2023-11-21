package com.raisin.backend.domain.service;

import com.raisin.backend.domain.model.FixtureObject;

public interface FixtureReporterService {
    void reportJoinedFixture(FixtureObject fixtureObject);
    void handleRetry();
    void reportAllOrphaned();
}
