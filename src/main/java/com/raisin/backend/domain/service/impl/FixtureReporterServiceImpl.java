package com.raisin.backend.domain.service.impl;

import com.raisin.backend.domain.model.FixtureKind;
import com.raisin.backend.domain.model.FixtureObject;
import com.raisin.backend.domain.service.FixtureReporterService;
import com.raisin.backend.infrastructure.datasource.FixtureRepository;
import com.raisin.backend.infrastructure.http.client.FixtureClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class FixtureReporterServiceImpl implements FixtureReporterService {

    private final FixtureClient fixtureClient;
    private final FixtureRepository fixtureRepository;

    @Override
    public void reportJoinedFixture(FixtureObject fixtureObject) {
        fixtureRepository.deleteFromList(fixtureObject.getId());
        fixtureObject.setKind(FixtureKind.joined.name());
        reportFixture(fixtureObject);
    }

    @Async
    @Scheduled(fixedRate = 500)
    @Override
    public void handleRetry() {
        fixtureRepository
                .findAllRetry()
                .forEach(this::reportFixture);
    }

    @Async
    @Override
    public void reportAllOrphaned() {
        fixtureRepository
                .findAll()
                .forEach(fixtureObject -> {
                    fixtureRepository.deleteFromList(fixtureObject.getId());
                    fixtureObject.setKind(FixtureKind.orphaned.name());
                    reportFixture(fixtureObject);
                });
    }

    private void reportFixture(FixtureObject fixtureObject) {
        if (fixtureRepository.isReported(fixtureObject.getId())) {
            log.debug("Fixture already reported: " + fixtureObject);
            return;
        }
        var httpResponse = fixtureClient.reportFixture(fixtureObject);
        System.out.println(fixtureObject);
        httpResponse.ifPresentOrElse(response -> {
            if ("ok".equalsIgnoreCase(response.getStatus()) ||
                    "success".equalsIgnoreCase(response.getStatus())) {
                log.info("Fixture reported successfully: " + fixtureObject);
                fixtureRepository.addToReported(fixtureObject);
                deleteFromRetryIfExists(fixtureObject);
            } else if ("fail".equals(response.getStatus())) {
                addToRetryIfNotExist(fixtureObject);
            } else {
                log.debug("Fixture report returns unknown status: {}", response);
                addToRetryIfNotExist(fixtureObject);
            }
        }, () -> addToRetryIfNotExist(fixtureObject));
    }

    private void deleteFromRetryIfExists(FixtureObject fixtureObject) {
        if (fixtureRepository.existOnRetry(fixtureObject.getId())) {
            log.debug("Fixture reported successfully, deleted from retry: {}", fixtureObject);
            fixtureRepository.deleteFromRetry(fixtureObject.getId());
        }
    }

    private void addToRetryIfNotExist(FixtureObject fixtureObject) {
        if (!fixtureRepository.existOnRetry(fixtureObject.getId())) {
            log.debug("Fixture fail to report added to retry: {}", fixtureObject);
            fixtureRepository.addToRetry(fixtureObject);
        }
    }

}
