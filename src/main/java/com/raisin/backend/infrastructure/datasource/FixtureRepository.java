package com.raisin.backend.infrastructure.datasource;

import com.raisin.backend.domain.model.FixtureObject;

import java.util.List;

// simulation of JPA repository interface
// this interface is used by the domain layer
// to access the data layer
public interface FixtureRepository {
    void addToList(FixtureObject element);

    void deleteFromList(String id);

    boolean existsOnList(String id);

    void addToRetry(FixtureObject element);

    void deleteFromRetry(String id);

    boolean existOnRetry(String id);

    List<FixtureObject> findAll();

    List<FixtureObject> findAllRetry();

    void addToReported(FixtureObject element);

    boolean isReported(String id);
}
