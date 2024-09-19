package com.aryanabhi.recommendation.services;

import com.aryanabhi.recommendation.dto.ComparisonRequestDto;
import com.aryanabhi.recommendation.dto.ComparisonResponseDto;
import com.aryanabhi.recommendation.entity.Car;
import com.aryanabhi.recommendation.exception.ResourceNotFoundException;
import com.aryanabhi.recommendation.repository.CarRepository;
import com.aryanabhi.recommendation.service.ComparisonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComparisonServiceImplTest {

    @InjectMocks
    @Spy
    private ComparisonServiceImpl comparisonServiceImpl;

    @Mock
    private CarRepository carRepositoryMock;

    private Car santro;
    private Car elantra;
    private Car i10;

    private ComparisonRequestDto requestWithoutHideSimilarities;
    private ComparisonRequestDto requestWithHideSimilaritiesTrue;
    private ComparisonRequestDto requestWithHideSimilaritiesFalse;

    private static final String SEDAN = "SEDAN";
    private static final String HATCHBACK = "HATCHBACK";
    private static final String SANTRO = "SANTRO";
    private static final String ELANTRA = "ELANTRA";
    private static final String I10 = "I10";
    private static final String HYUNDAI = "HYUNDAI";
    private static final String EXCEPTION_OCCURRED = "No car exists for id: ";


    @BeforeEach
    public void setup() {
        santro = buildCar().toBuilder().id(1L).name(SANTRO).type(HATCHBACK).build();
        elantra = buildCar().toBuilder().id(2L).name(ELANTRA).type(SEDAN).build();
        i10 = buildCar().toBuilder().id(3L).name(I10).type(HATCHBACK).build();

        requestWithoutHideSimilarities = buildComparisonRequestDto();
        requestWithHideSimilaritiesTrue = buildComparisonRequestDto().toBuilder().hideSimilarities(true).build();
        requestWithHideSimilaritiesFalse = buildComparisonRequestDto().toBuilder().hideSimilarities(false).build();
    }


    @Test
    public void compareCars_nullHideSimilarities_success() throws ResourceNotFoundException {
        when(carRepositoryMock.findById(1L)).thenReturn(Optional.of(santro));
        when(carRepositoryMock.findById(2L)).thenReturn(Optional.of(elantra));
        when(carRepositoryMock.findById(3L)).thenReturn(Optional.of(i10));

        ComparisonResponseDto response = comparisonServiceImpl.compareCars(requestWithoutHideSimilarities);

        ComparisonResponseDto expected = buildComparisonResponseDto();

        assertNotNull(response);
        assertEquals(expected.getIds(), response.getIds());
        assertEquals(expected.getNames(), response.getNames());
        assertEquals(expected.getTypes(), response.getTypes());
        assertEquals(expected.getCompanies(), response.getCompanies());
        assertEquals(expected.getYears(), response.getYears());
        assertEquals(expected.getSeatings(), response.getSeatings());
        assertEquals(expected.getAutomaticAvailibility(), response.getAutomaticAvailibility());
        assertEquals(expected.getHorsepowers(), response.getHorsepowers());
        assertEquals(expected.getMileages(), response.getMileages());
        assertEquals(expected.getPrices(), response.getPrices());
        assertEquals(expected.getScores(), response.getScores());

        verify(carRepositoryMock, times(3)).findById(anyLong());
    }

    @Test
    public void compareCars_hideSimilaritiesFalse_success() throws ResourceNotFoundException {
        when(carRepositoryMock.findById(1L)).thenReturn(Optional.of(santro));
        when(carRepositoryMock.findById(2L)).thenReturn(Optional.of(elantra));
        when(carRepositoryMock.findById(3L)).thenReturn(Optional.of(i10));

        ComparisonResponseDto response = comparisonServiceImpl.compareCars(requestWithHideSimilaritiesFalse);

        ComparisonResponseDto expected = buildComparisonResponseDto();

        assertNotNull(response);
        assertEquals(expected.getIds(), response.getIds());
        assertEquals(expected.getNames(), response.getNames());
        assertEquals(expected.getTypes(), response.getTypes());
        assertEquals(expected.getCompanies(), response.getCompanies());
        assertEquals(expected.getYears(), response.getYears());
        assertEquals(expected.getSeatings(), response.getSeatings());
        assertEquals(expected.getAutomaticAvailibility(), response.getAutomaticAvailibility());
        assertEquals(expected.getHorsepowers(), response.getHorsepowers());
        assertEquals(expected.getMileages(), response.getMileages());
        assertEquals(expected.getPrices(), response.getPrices());
        assertEquals(expected.getScores(), response.getScores());

        verify(carRepositoryMock, times(3)).findById(anyLong());
    }

    @Test
    public void compareCars_hideSimilaritiesTrue_success() throws ResourceNotFoundException {
        when(carRepositoryMock.findById(1L)).thenReturn(Optional.of(santro));
        when(carRepositoryMock.findById(2L)).thenReturn(Optional.of(elantra));
        when(carRepositoryMock.findById(3L)).thenReturn(Optional.of(i10));

        ComparisonResponseDto response = comparisonServiceImpl.compareCars(requestWithHideSimilaritiesTrue);

        ComparisonResponseDto expected = buildComparisonResponseDtoWithoutSimilarities();

        assertNotNull(response);
        assertEquals(expected.getIds(), response.getIds());
        assertEquals(expected.getNames(), response.getNames());
        assertEquals(expected.getTypes(), response.getTypes());
        assertNull(expected.getCompanies());
        assertNull(expected.getYears());
        assertNull(expected.getSeatings());
        assertNull(expected.getAutomaticAvailibility());
        assertNull(expected.getHorsepowers());
        assertNull(expected.getMileages());
        assertNull(expected.getPrices());
        assertNull(expected.getScores());

        verify(carRepositoryMock, times(3)).findById(anyLong());
    }

    @Test
    public void compareCars_nullResponse() throws ResourceNotFoundException {
        long id = 5L;
        when(carRepositoryMock.findById(id)).thenReturn(Optional.empty());

        Exception e = assertThrows(ResourceNotFoundException.class, () -> {
            comparisonServiceImpl.compareCars(requestWithHideSimilaritiesTrue.toBuilder()
                            .ids(new ArrayList<>(Arrays.asList(id, 6L, 7L)))
                    .build());
        });

        assertEquals(EXCEPTION_OCCURRED + id, e.getMessage());

        verify(carRepositoryMock, times(1)).findById(id);
    }

    private Car buildCar() {
        return Car.builder()
                .company(HYUNDAI)
                .year(2015)
                .seating(5)
                .automaticAvailable(true)
                .horsepower(130.0)
                .mileage(15.0)
                .price(300000.0)
                .score(450.80)
                .build();
    }

    private ComparisonRequestDto buildComparisonRequestDto() {
        return ComparisonRequestDto.builder()
                .ids(buildIdsList())
                .build();
    }

    private List<Long> buildIdsList() {
        return new ArrayList<>(Arrays.asList(1L,2L,3L));
    }

    private ComparisonResponseDto buildComparisonResponseDtoWithoutSimilarities() {
        return buildComparisonResponseDto().toBuilder()
                .companies(null)
                .years(null)
                .seatings(null)
                .automaticAvailibility(null)
                .horsepowers(null)
                .mileages(null)
                .prices(null)
                .scores(null)
                .build();
    }

    private ComparisonResponseDto buildComparisonResponseDto() {
        return ComparisonResponseDto.builder()
                .ids(new ArrayList<>(Arrays.asList(1L, 2L, 3L)))
                .names(new ArrayList<>(Arrays.asList(SANTRO, ELANTRA, I10)))
                .types(new ArrayList<>(Arrays.asList(HATCHBACK, SEDAN, HATCHBACK)))
                .companies(new ArrayList<>(Arrays.asList(HYUNDAI)))
                .years(new ArrayList<>(Arrays.asList(2015)))
                .seatings(new ArrayList<>(Arrays.asList(5)))
                .automaticAvailibility(new ArrayList<>(Arrays.asList(true)))
                .horsepowers(new ArrayList<>(Arrays.asList(130.0)))
                .mileages(new ArrayList<>(Arrays.asList(15.0)))
                .prices(new ArrayList<>(Arrays.asList(300000.0)))
                .scores(new ArrayList<>(Arrays.asList(450.80)))
                .build();
    }
}
