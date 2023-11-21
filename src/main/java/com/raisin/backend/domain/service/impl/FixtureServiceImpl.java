package com.raisin.backend.domain.service.impl;

import com.raisin.backend.domain.model.FixtureMapper;
import com.raisin.backend.domain.model.FixtureObject;
import com.raisin.backend.domain.service.FixtureReporterService;
import com.raisin.backend.domain.service.FixtureService;
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
public class FixtureServiceImpl implements FixtureService {

    public static boolean isADone = false;
    public static boolean isBDone = false;

    private final FixtureClient fixtureClient;
    private final FixtureRepository fixtureRepository;
    private final FixtureMapper fixtureMapper;
    private final FixtureReporterService fixtureReporterService;


    @Async
    @Scheduled(fixedRate = 100)
    @Override
    public void getFromSourceA() {
        if (!isADone) {
            fixtureClient.getFixtureFromSourceA()
                    .ifPresent(responseA -> {
                                if ("done".equalsIgnoreCase(responseA.getStatus())) {
                                    isADone = true;
                                } else {
                                    addFixture(fixtureMapper.toFRFromSourceA(responseA));
                                }
                            }
                    );
        }
    }

    @Async
    @Scheduled(fixedRate = 100)
    @Override
    public void getFromSourceB() {
        if (!isBDone) {
            fixtureClient.getFixtureFromSourceB()
                    .ifPresent(responseB -> {
                        if (responseB.getDone() != null && responseB.getId() == null) {
                            isBDone = true;
                        } else {
                            addFixture(fixtureMapper.toFRFromSourceB(responseB));
                        }
                    });
        }
    }

    @Scheduled(fixedRate = 2000)
    @Override
    public void sendAllOrphans() {
        if (isADone && isBDone) {
            fixtureReporterService.reportAllOrphaned();

            if (fixtureRepository.findAllRetry().isEmpty()
                    && fixtureRepository.findAll().isEmpty()) {
                System.exit(0); // this is barbaric but it's a fun task anyway
            }
        }
    }

    private void addFixture(FixtureObject fixtureObject) {
        if (fixtureRepository.existsOnList(fixtureObject.getId())) {
            // send it as joined
            fixtureReporterService.reportJoinedFixture(fixtureObject);
        } else {
            fixtureRepository.addToList(fixtureObject);
        }
    }

}
