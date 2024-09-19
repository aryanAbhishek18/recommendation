package com.aryanabhi.recommendation.services;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.entity.Car;
import com.aryanabhi.recommendation.exception.ResourceNotFoundException;
import com.aryanabhi.recommendation.repository.CarRepository;
import com.aryanabhi.recommendation.service.RecommendationServiceImpl;
import com.aryanabhi.recommendation.utility.ScoreUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceImplTest {

    @InjectMocks
    @Spy
    private RecommendationServiceImpl recommendationServiceImpl;

    @Mock
    private CarRepository carRepositoryMock;

    @Mock
    private ModelMapper modelMapperMock;

    @Mock
    private ScoreUtility scoreUtilityMock;

    private CarDto carDto1;
    private CarDto carDto2;
    private CarDto carDto3;
    private Car car1;
    private Car car2;
    private Car car3;
    private List<CarDto> carDtoList;
    private List<Car> carList;

    private static final String EXCEPTION = "No car exists for id: ";
    private static final String SANTRO = "SANTRO";
    private static final String ALTO = "ALTO";
    private static final String RITZ = "RITZ";
    private static final String HATCHBACK = "HATCHBACK";
    private static final String HYUNDAI = "HYUNDAI";

    @BeforeEach
    public void setup() {
        carDto1 = buildCarDto().toBuilder().name(SANTRO).build();
        carDto2 = buildCarDto().toBuilder().name(ALTO).build();
        carDto3 = buildCarDto().toBuilder().name(RITZ).build();
        carDtoList = new ArrayList<>(Arrays.asList(carDto1, carDto2, carDto3));

        car1 = buildCar().toBuilder().id(1L).name(SANTRO).build();
        car2 = buildCar().toBuilder().id(2L).name(ALTO).build();
        car3 = buildCar().toBuilder().id(3L).name(RITZ).build();
        carList = new ArrayList<>(Arrays.asList(car1, car2, car3));
    }

    @Test
    public void getRecommendations_success() throws ResourceNotFoundException {
        long id = 1L;
        when(carRepositoryMock.findById(id)).thenReturn(Optional.of(car1));
        when(carRepositoryMock.findByType(HATCHBACK)).thenReturn(carList);
        when(modelMapperMock.map(car1, CarDto.class)).thenReturn(carDto1);
        when(modelMapperMock.map(car2, CarDto.class)).thenReturn(carDto2);
        when(modelMapperMock.map(car3, CarDto.class)).thenReturn(carDto3);
        when(scoreUtilityMock.findMostRecommendedForScore(id, car1.getScore(), carDtoList, 5))
                .thenReturn(carDtoList);

        List<CarDto> response = recommendationServiceImpl.getRecommendations(id, 5);

        assertNotNull(response);
        assertEquals(carDtoList, response);

        verify(carRepositoryMock, times(1)).findById(1L);
        verify(carRepositoryMock, times(1)).findByType(HATCHBACK);
        verify(modelMapperMock, times(1)).map(car1, CarDto.class);
        verify(modelMapperMock, times(1)).map(car2, CarDto.class);
        verify(modelMapperMock, times(1)).map(car3, CarDto.class);
        verify(scoreUtilityMock, times(1))
                .findMostRecommendedForScore(id, car1.getScore(), carDtoList, 5);
    }

    @Test
    public void getRecommendations_throwsException() {
        long id = 1L;
        when(carRepositoryMock.findById(id)).thenReturn(Optional.empty());

        Exception e = assertThrows(ResourceNotFoundException.class, () -> {
           recommendationServiceImpl.getRecommendations(id, 5);
        });
        assertEquals(EXCEPTION + id, e.getMessage());

        verify(carRepositoryMock, times(1)).findById(id);
        verify(carRepositoryMock, times(0)).findByType(anyString());
        verify(modelMapperMock, times(0)).map(car1, CarDto.class);
        verify(scoreUtilityMock, times(0))
                .findMostRecommendedForScore(id,car1.getScore(),carDtoList,5);
    }

    @Test
    public void getRecommendations_nullResponse() throws ResourceNotFoundException {
        long id = 1L;
        when(carRepositoryMock.findById(id)).thenReturn(Optional.of(car1));
        when(carRepositoryMock.findByType(HATCHBACK)).thenReturn(new ArrayList<>());

        List<CarDto> response = recommendationServiceImpl.getRecommendations(id, 5);

        assertNull(response);

        verify(carRepositoryMock, times(1)).findById(1L);
        verify(carRepositoryMock, times(1)).findByType(HATCHBACK);
        verify(modelMapperMock, times(0)).map(car1, CarDto.class);
        verify(scoreUtilityMock, times(0))
                .findMostRecommendedForScore(id, car1.getScore(), carDtoList, 5);
    }

    private CarDto buildCarDto() {
        return CarDto.builder()
                .type(HATCHBACK)
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

    private Car buildCar() {
        return Car.builder()
                .type(HATCHBACK)
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
}
