package com.raisin.backend.infrastructure.datasource;

import com.raisin.backend.domain.model.FixtureObject;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryFixtureStore {
    // all data stores are protected no access outside the datasource layer
    protected final Map<String, FixtureObject> store = new ConcurrentHashMap<>();
    protected final Map<String, FixtureObject> retryStore = new ConcurrentHashMap<>();
    protected final Map<String, FixtureObject> reported = new ConcurrentHashMap<>();
}
