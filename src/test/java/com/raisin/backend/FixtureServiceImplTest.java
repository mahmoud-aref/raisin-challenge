package com.raisin.backend;

import com.raisin.backend.domain.model.FixtureMapper;
import com.raisin.backend.domain.model.FixtureObject;
import com.raisin.backend.domain.service.FixtureReporterService;
import com.raisin.backend.domain.service.impl.FixtureServiceImpl;
import com.raisin.backend.infrastructure.datasource.FixtureRepository;
import com.raisin.backend.infrastructure.http.client.FixtureClient;
import com.raisin.backend.infrastructure.http.client.model.SourceAResponse;
import com.raisin.backend.infrastructure.http.client.model.SourceBResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FixtureServiceImplTest {

    @Mock
    private FixtureClient fixtureClient;

    @Mock
    private FixtureRepository fixtureRepository;

    @Mock
    private FixtureMapper fixtureMapper;

    @Mock
    private FixtureReporterService fixtureReporterService;

    @InjectMocks
    private FixtureServiceImpl fixtureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFromSourceA() {

        var mockResponse = SourceAResponse.builder()
                .status("ok")
                .id(UUID.randomUUID().toString())
                .build();

        when(fixtureClient.getFixtureFromSourceA())
                .thenReturn(Optional.of(mockResponse));

        var fixtureObjectMap = FixtureObject.builder().id(mockResponse.getId()).build();

        when(fixtureMapper.toFRFromSourceA(mockResponse))
                .thenReturn(fixtureObjectMap);

        // Call the method again
        fixtureService.getFromSourceA();

        // Verify that fixtureRepository.addToList() is called when response is present
        verify(fixtureRepository, times(1)).addToList(any());
    }

    @Test
    void testGetFromSourceB() {

        var mockResponse = SourceBResponse.builder()
                .id(new SourceBResponse.IdWrapper(UUID.randomUUID().toString()))
                .build();

        when(fixtureClient.getFixtureFromSourceB())
                .thenReturn(Optional.of(mockResponse));

        var fixtureObjectMap = FixtureObject.builder().id(mockResponse.getId().getValue()).build();

        when(fixtureMapper.toFRFromSourceB(mockResponse))
                .thenReturn(fixtureObjectMap);

        // Call the method again
        fixtureService.getFromSourceB();

        // Verify that fixtureRepository.addToList() is called when response is present
        verify(fixtureRepository, times(1)).addToList(any());
    }


    @Test
    void testSendAllOrphans() {

        // test all orphans sent when both sources are done
        FixtureServiceImpl.isADone = true;
        FixtureServiceImpl.isBDone = true;

        FixtureObject mockFixtureObject = mock(FixtureObject.class);

        when(fixtureRepository.findAllRetry()).thenReturn(Collections.singletonList(mockFixtureObject));
        when(fixtureRepository.findAll()).thenReturn(Collections.singletonList(mockFixtureObject));


        // Call the method again
        fixtureService.sendAllOrphans();

        // Verify that reportAllOrphaned() is called
        verify(fixtureReporterService, times(1)).reportAllOrphaned();
    }

}
