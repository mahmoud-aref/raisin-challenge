package com.raisin.backend.infrastructure.datasource;

import com.raisin.backend.domain.model.FixtureObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@AllArgsConstructor
@Slf4j
public class FixtureRepositoryImpl implements FixtureRepository { // Fixture DAO

    private final InMemoryFixtureStore inMemoryFixtureStore;

    @Override
    public void addToList(FixtureObject element) {
        log.info("Adding element to datastore: " + element);
        inMemoryFixtureStore.store.put(element.getId(), element);
    }

    @Override
    public void deleteFromList(String id) {
        log.info("Deleting element from datastore: " + id);
        inMemoryFixtureStore.store.remove(id);
    }

    @Override
    public boolean existsOnList(String id) {
        return inMemoryFixtureStore.store.containsKey(id);
    }

    @Override
    public void addToRetry(FixtureObject element) {
        log.info("Adding element to retry list: " + element);
        inMemoryFixtureStore.retryStore.put(element.getId(), element);
    }

    @Override
    public void deleteFromRetry(String id) {
        log.info("Deleting element from retry store: " + id);
        inMemoryFixtureStore.retryStore.remove(id);
    }

    @Override
    public boolean existOnRetry(String id) {
        return inMemoryFixtureStore.retryStore.containsKey(id);
    }

    @Override
    public List<FixtureObject> findAll() {
        return inMemoryFixtureStore.store.values().stream().toList();
    }

    @Override
    public List<FixtureObject> findAllRetry() {
        return inMemoryFixtureStore.retryStore.values().stream().toList();
    }

    @Override
    public void addToReported(FixtureObject element) {
        log.info("Adding element to reported list: " + element);
        inMemoryFixtureStore.reported.put(element.getId(), element);
    }

    @Override
    public boolean isReported(String id) {
        return this.inMemoryFixtureStore.reported.containsKey(id);
    }
}
