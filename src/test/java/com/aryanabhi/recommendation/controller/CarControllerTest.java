package com.aryanabhi.recommendation.controller;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.dto.WeightDto;
import com.aryanabhi.recommendation.exception.ResourceNotFoundException;
import com.aryanabhi.recommendation.service.CarService;
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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarControllerTest {

    @InjectMocks
    @Spy
    private CarController carController;

    @Mock
    private CarService carServiceMock;

    @Mock
    private WeightService weightServiceMock;

    private CarDto car1;
    private CarDto car2;
    private CarDto car3;
    private CarDto savedCar1;
    private CarDto savedCar2;
    private CarDto savedCar3;
    private List<CarDto> carDtoList;
    private List<CarDto> savedCarDtoList;

    private WeightDto weightDto1;
    private WeightDto weightDto2;
    private List<WeightDto> weightDtoList;

    private static final String EXCEPTION = "EXCEPTION OCCURRED!";
    private static final String SANTRO = "SANTRO";
    private static final String ALTO = "ALTO";
    private static final String RITZ = "RITZ";
    private static final String SUV = "SUV";
    private static final String SEDAN = "SEDAN";
    private static final String HYUNDAI = "HYUNDAI";
    private static final String CARS_DELETED = "Deleted all cars!";
    private static final String CAR_DELETED_FOR_ID = "Deleted car with id: ";


    @BeforeEach
    public void setup() {
        car1 = buildCarDto().toBuilder().name(SANTRO).build();
        car2 = buildCarDto().toBuilder().name(ALTO).build();
        car3 = buildCarDto().toBuilder().name(RITZ).build();
        carDtoList = new ArrayList<>(Arrays.asList(car1, car2, car3));

        savedCar1 = buildCarDto().toBuilder().id(1L).name(SANTRO).build();
        savedCar2 = buildCarDto().toBuilder().id(2L).name(ALTO).build();
        savedCar3 = buildCarDto().toBuilder().id(3L).name(RITZ).build();
        savedCarDtoList = new ArrayList<>(Arrays.asList(savedCar1, savedCar2, savedCar3));

        weightDto1 = getWeightDto(1L, SUV);
        weightDto2 = getWeightDto(2L, SEDAN);
        weightDtoList = new ArrayList<>(Arrays.asList(weightDto1, weightDto2));
    }

    @Test
    public void fetchAllCars_success() {
        when(carServiceMock.getAllCars()).thenReturn(savedCarDtoList);

        ResponseEntity<List<CarDto>> response = carController.fetchAllCars();

        assertNotNull(response.getBody());
        assertEquals(savedCarDtoList, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(carServiceMock, times(1)).getAllCars();
    }

    @Test
    public void fetchCarById_success() throws ResourceNotFoundException {
        when(carServiceMock.getCar(1L)).thenReturn(savedCar1);

        ResponseEntity<CarDto> response = carController.fetchCarById(1L);

        assertNotNull(response.getBody());
        assertEquals(savedCar1, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(carServiceMock, times(1)).getCar(1L);
    }

    @Test
    public void createCars_success() {
        when(weightServiceMock.getRankWeights()).thenReturn(weightDtoList);
        when(carServiceMock.createCars(carDtoList, weightDtoList)).thenReturn(savedCarDtoList);

        ResponseEntity<List<CarDto>> response = carController.createCars(carDtoList);

        assertNotNull(response.getBody());
        assertEquals(savedCarDtoList, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(carServiceMock, times(1)).createCars(carDtoList, weightDtoList);
    }

    @Test
    public void deleteAllCars_success() {
        ResponseEntity<String> response = carController.deleteAllCars();

        assertNotNull(response.getBody());
        assertEquals(CARS_DELETED, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(carServiceMock, times(1)).deleteAllCars();
    }

    @Test
    public void deleteCarById_success() throws ResourceNotFoundException {
        long id = 1L;
        when(carServiceMock.getCar(id)).thenReturn(savedCar1);
        ResponseEntity<String> response = carController.deleteCarById(id);

        assertNotNull(response.getBody());
        assertEquals(CAR_DELETED_FOR_ID + id, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(carServiceMock, times(1)).deleteCarById(1L);
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

    private WeightDto getWeightDto(Long id, String type) {
        return WeightDto.builder()
                .id(id)
                .type(type)
                .year(0.5)
                .seating(0.5)
                .horsepower(0.4)
                .mileage(0.3)
                .price(0.005)
                .build();
    }
}
