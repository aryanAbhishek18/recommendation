package com.aryanabhi.recommendation.controller;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.exception.InvalidInputException;
import com.aryanabhi.recommendation.exception.ResourceNotFoundException;
import com.aryanabhi.recommendation.service.RecommendationService;
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
import java.util.Optional;

import static com.aryanabhi.recommendation.Constants.CAR_RECOMMENDATION_DEFAULT_LIMIT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecommendationControllerTest {

    @InjectMocks
    @Spy
    private RecommendationController recommendationController;

    @Mock
    private RecommendationService recommendationServiceMock;

    private CarDto car1;
    private CarDto car2;
    private CarDto car3;
    private CarDto car4;

    private static final String EXCEPTION = "EXCEPTION OCCURRED!";
    private static final String SANTRO = "SANTRO";
    private static final String ALTO = "ALTO";
    private static final String RITZ = "RITZ";
    private static final String I10 = "I10";
    private static final String HYUNDAI = "HYUNDAI";


    @BeforeEach
    public void setup() {
        car1 = buildCarDto().toBuilder().id(1L).name(SANTRO).build();
        car2 = buildCarDto().toBuilder().id(2L).name(ALTO).build();
        car3 = buildCarDto().toBuilder().id(3L).name(RITZ).build();
        car4 = buildCarDto().toBuilder().id(4L).name(I10).build();
    }

    @Test
    public void recommendCars_withoutLimit_success() throws InvalidInputException, ResourceNotFoundException {
        List<CarDto> carList = new ArrayList<>(Arrays.asList(car1, car2, car4));
        when(recommendationServiceMock.getRecommendations(3L, CAR_RECOMMENDATION_DEFAULT_LIMIT)).thenReturn(carList);

        ResponseEntity<List<CarDto>> response = recommendationController.recommendCars(3L, null);

        assertNotNull(response.getBody());
        assertEquals(carList, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(recommendationServiceMock, times(1))
                .getRecommendations(3L, CAR_RECOMMENDATION_DEFAULT_LIMIT);
    }

    @Test
    public void recommendCars_withoutLimit_nullResponse() throws InvalidInputException, ResourceNotFoundException {
        when(recommendationServiceMock.getRecommendations(5L, CAR_RECOMMENDATION_DEFAULT_LIMIT))
                .thenThrow(new ResourceNotFoundException(EXCEPTION));

        ResponseEntity<List<CarDto>> response = recommendationController.recommendCars(5L, null);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(recommendationServiceMock, times(1))
                .getRecommendations(5L, CAR_RECOMMENDATION_DEFAULT_LIMIT);
    }

    @Test
    public void recommendCars_withLimit_success() throws InvalidInputException, ResourceNotFoundException {
        List<CarDto> carList = new ArrayList<>(Arrays.asList(car1, car3));
        when(recommendationServiceMock.getRecommendations(4L, 2)).thenReturn(carList);

        ResponseEntity<List<CarDto>> response = recommendationController.recommendCars(4L, Optional.of(2));

        assertNotNull(response.getBody());
        assertEquals(carList, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(recommendationServiceMock, times(1))
                .getRecommendations(4L, 2);
    }

    @Test
    public void recommendCars_withLimit_nullResponse() throws InvalidInputException, ResourceNotFoundException {
        when(recommendationServiceMock.getRecommendations(1L, 2))
                .thenThrow(new ResourceNotFoundException(EXCEPTION));

        ResponseEntity<List<CarDto>> response = recommendationController.recommendCars(1L, Optional.of(2));

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(recommendationServiceMock, times(1))
                .getRecommendations(1L, 2);
    }

    private CarDto buildCarDto() {
        return CarDto.builder()
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
