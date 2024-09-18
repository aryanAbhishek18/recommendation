package com.aryanabhi.recommendation.controller;

import com.aryanabhi.recommendation.dto.WeightDto;
import com.aryanabhi.recommendation.exception.ResourceNotFoundException;
import com.aryanabhi.recommendation.service.WeightService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeightControllerTest {

    @InjectMocks
    @Spy
    private WeightController weightController;

    @Mock
    private WeightService weightServiceMock;

    private WeightDto weightDto1;
    private WeightDto weightDto2;
    private WeightDto savedWeightDto1;
    private WeightDto savedWeightDto2;
    private List<WeightDto> requestDtoList;
    private List<WeightDto> responseDtoList;

    private static final String DELETE_MESSAGE = "Deleted all weight records!";
    private static final String SUV = "SUV";
    private static final String SEDAN = "SEDAN";
    private static final String RANDOM_TYPE = "RANDOM_TYPE";
    private static final String EXCEPTION_OCCURRED = "EXCEPTION OCCURRED!";

    @BeforeEach
    public void setup() {
        weightDto1 = getWeightDto(SUV);
        weightDto2 = getWeightDto(SEDAN);
        savedWeightDto1 = weightDto1.toBuilder().id(1L).build();
        savedWeightDto2 = weightDto2.toBuilder().id(2L).build();

        requestDtoList = new ArrayList<>();
        requestDtoList.add(weightDto1);
        requestDtoList.add(weightDto2);

        responseDtoList = new ArrayList<>();
        responseDtoList.add(savedWeightDto1);
        responseDtoList.add(savedWeightDto2);

    }

    @Test
    public void createWeights_success() {
        when(weightServiceMock.createRankWeights(any())).thenReturn(responseDtoList);

        ResponseEntity<List<WeightDto>> response = weightController.createWeights(requestDtoList);

        assertNotNull(response.getBody());
        assertEquals(responseDtoList, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(weightServiceMock, times(1)).createRankWeights(any());
    }

    @Test
    public void createWeights_NullResponse() {
        when(weightServiceMock.createRankWeights(any())).thenReturn(null);

        ResponseEntity<List<WeightDto>> response = weightController.createWeights(requestDtoList);

        assertNull(response.getBody());
        assertEquals(HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }

    @Test
    public void getWeights_success() {
        when(weightServiceMock.getRankWeights()).thenReturn(responseDtoList);

        ResponseEntity<List<WeightDto>> response = weightController.getWeights();

        assertNotNull(response.getBody());
        assertEquals(responseDtoList, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(weightServiceMock, times(1)).getRankWeights();
    }

    @Test
    public void getWeightByType_success() throws ResourceNotFoundException {
        when(weightServiceMock.getRankWeightByType(anyString())).thenReturn(savedWeightDto1);

        ResponseEntity<WeightDto> response = weightController.getWeightByType(SUV);

        assertNotNull(response.getBody());
        assertEquals(savedWeightDto1, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(weightServiceMock, times(1)).getRankWeightByType(SUV);
    }

    @Test
    public void getWeightByType_nullResponse() throws ResourceNotFoundException {
        when(weightServiceMock.getRankWeightByType(anyString()))
                .thenThrow(new ResourceNotFoundException(EXCEPTION_OCCURRED));

        ResponseEntity<WeightDto> response = weightController.getWeightByType(RANDOM_TYPE);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(weightServiceMock, times(1)).getRankWeightByType(RANDOM_TYPE);
    }

    @Test
    public void deleteWeights_success() {
        ResponseEntity<String> response = weightController.deleteWeights();

        assertNotNull(response.getBody());
        assertEquals(DELETE_MESSAGE, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(weightServiceMock, times(1)).deleteRankWeights();
    }

    private WeightDto getWeightDto(String type) {
        return WeightDto.builder()
                .type(type)
                .year(0.5)
                .seating(0.5)
                .horsepower(0.4)
                .mileage(0.3)
                .price(0.005)
                .build();
    }
}
