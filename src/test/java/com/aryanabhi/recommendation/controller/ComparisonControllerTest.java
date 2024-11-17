package com.aryanabhi.recommendation.controller;

import com.aryanabhi.recommendation.dto.ComparisonRequestDto;
import com.aryanabhi.recommendation.dto.ComparisonResponseDto;
import com.aryanabhi.recommendation.exception.ResourceNotFoundException;
import com.aryanabhi.recommendation.service.ComparisonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComparisonControllerTest {

    @InjectMocks
    @Spy
    private ComparisonController comparisonController;

    @Mock
    private ComparisonService comparisonServiceMock;

    private ComparisonRequestDto requestWithoutHideSimilarities;
    private ComparisonRequestDto requestWithHideSimilaritiesTrue;
    private ComparisonRequestDto requestWithHideSimilaritiesFalse;
    private ComparisonResponseDto comparisonResponseDto;

    private static final String SEDAN = "SEDAN";
    private static final String HATCHBACK = "HATCHBACK";
    private static final String SANTRO = "SANTRO";
    private static final String ELANTRA = "ELANTRA";

    @BeforeEach
    public void setup() {
        requestWithoutHideSimilarities = buildComparisonRequestDto();
        requestWithHideSimilaritiesTrue = buildComparisonRequestDto().toBuilder().hideSimilarities(true).build();
        requestWithHideSimilaritiesFalse = buildComparisonRequestDto().toBuilder().hideSimilarities(false).build();
        comparisonResponseDto = buildComparisonResponseDto();
    }

    @Test
    public void compareCars_noHideSimilarities_success() throws ResourceNotFoundException {
        when(comparisonServiceMock.compareCars(requestWithoutHideSimilarities))
                .thenReturn(comparisonResponseDto);

        ResponseEntity<ComparisonResponseDto> response = comparisonController
                .compareCars(requestWithoutHideSimilarities);

        assertNotNull(response.getBody());
        assertEquals(comparisonResponseDto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(comparisonServiceMock, times(1)).compareCars(any());

    }

    @Test
    public void compareCars_hideSimilaritiesFalse_success() throws ResourceNotFoundException {
        when(comparisonServiceMock.compareCars(requestWithHideSimilaritiesFalse))
                .thenReturn(comparisonResponseDto);

        ResponseEntity<ComparisonResponseDto> response = comparisonController
                .compareCars(requestWithHideSimilaritiesFalse);

        assertNotNull(response.getBody());
        assertEquals(comparisonResponseDto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(comparisonServiceMock, times(1)).compareCars(any());

    }

    @Test
    public void compareCars_hideSimilaritiesTrue_success() throws ResourceNotFoundException {
        when(comparisonServiceMock.compareCars(requestWithHideSimilaritiesTrue))
                .thenReturn(comparisonResponseDto);

        ResponseEntity<ComparisonResponseDto> response = comparisonController
                .compareCars(requestWithHideSimilaritiesTrue);

        assertNotNull(response.getBody());
        assertEquals(comparisonResponseDto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(comparisonServiceMock, times(1)).compareCars(any());

    }

    private ComparisonRequestDto buildComparisonRequestDto() {
        return ComparisonRequestDto.builder()
                .ids(buildIdsList())
                .build();
    }

    private List<Long> buildIdsList() {
        return new ArrayList<>(Arrays.asList(1L,2L,3L));
    }

    private ComparisonResponseDto buildComparisonResponseDto() {
        return ComparisonResponseDto.builder()
                .names(new ArrayList<>(Arrays.asList(SANTRO, ELANTRA)))
                .types(new ArrayList<>(Arrays.asList(HATCHBACK, SEDAN)))
                .build();
    }
}
