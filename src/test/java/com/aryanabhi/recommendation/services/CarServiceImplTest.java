package com.aryanabhi.recommendation.services;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.dto.WeightDto;
import com.aryanabhi.recommendation.entity.Car;
import com.aryanabhi.recommendation.exception.ResourceNotFoundException;
import com.aryanabhi.recommendation.repository.CarRepository;
import com.aryanabhi.recommendation.service.CarServiceImpl;
import com.aryanabhi.recommendation.utility.ScoreUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static com.aryanabhi.recommendation.Constants.CAR_PAGE_SIZE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceImplTest {

    @InjectMocks
    @Spy
    private CarServiceImpl carServiceImpl;

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
    private static final String SEDAN = "SEDAN";
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
    public void getAllCars_success() {
        List<Car> firstPageCars = carList;
        Page<Car> firstPage = new PageImpl<>(firstPageCars, PageRequest.of(0, CAR_PAGE_SIZE), 4);
        when(carRepositoryMock.findAll(PageRequest.of(0, CAR_PAGE_SIZE))).thenReturn(firstPage);
        when(modelMapperMock.map(car1, CarDto.class)).thenReturn(carDto1);
        when(modelMapperMock.map(car2, CarDto.class)).thenReturn(carDto2);
        when(modelMapperMock.map(car3, CarDto.class)).thenReturn(carDto3);

        List<CarDto> response = carServiceImpl.getAllCars();

        assertNotNull(response);
        assertEquals(carDtoList, response);

        verify(carRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void getCar_success() throws ResourceNotFoundException {
        when(carRepositoryMock.findById(1L)).thenReturn(Optional.of(car1));
        when(modelMapperMock.map(car1, CarDto.class)).thenReturn(carDto1);

        CarDto response = carServiceImpl.getCar(1L);

        assertNotNull(response);
        assertEquals(carDto1, response);
    }

    @Test
    public void getCar_throwsException() throws ResourceNotFoundException {
        long id = 5L;
        when(carRepositoryMock.findById(id)).thenReturn(Optional.empty());

        Exception e = assertThrows(ResourceNotFoundException.class, () -> {
            carServiceImpl.getCar(id);
        });

        assertEquals(EXCEPTION + id, e.getMessage());

        verify(carRepositoryMock, times(1)).findById(id);
    }

    @Test
    public void createCars_success() {
        WeightDto w1 = buildWeightDto(1L, HATCHBACK);
        WeightDto w2 = buildWeightDto(2L, SEDAN);

        List<WeightDto> weightDtoList = new ArrayList<>();
        weightDtoList.add(w1);
        weightDtoList.add(w2);

        Map<String, WeightDto> typeToWeight = new HashMap<>();
        typeToWeight.put(HATCHBACK, w1);
        typeToWeight.put(SEDAN, w2);

        when(scoreUtilityMock.mapTypeToWeightDto(weightDtoList)).thenReturn(typeToWeight);
        when(modelMapperMock.map(carDto1, Car.class)).thenReturn(car1);
        when(modelMapperMock.map(carDto2, Car.class)).thenReturn(car2);
        when(modelMapperMock.map(carDto3, Car.class)).thenReturn(car3);
        when(scoreUtilityMock.calculateScore(carDto1,w1)).thenReturn(carDto1.getScore());
        when(scoreUtilityMock.calculateScore(carDto2,w1)).thenReturn(carDto2.getScore());
        when(scoreUtilityMock.calculateScore(carDto3,w1)).thenReturn(carDto3.getScore());
        when(carRepositoryMock.save(car1)).thenReturn(car1);
        when(carRepositoryMock.save(car2)).thenReturn(car2);
        when(carRepositoryMock.save(car3)).thenReturn(car3);
        when(modelMapperMock.map(car1, CarDto.class)).thenReturn(carDto1);
        when(modelMapperMock.map(car2, CarDto.class)).thenReturn(carDto2);
        when(modelMapperMock.map(car3, CarDto.class)).thenReturn(carDto3);

        List<CarDto> response = carServiceImpl.createCars(carDtoList, weightDtoList);

        assertNotNull(response);
        assertEquals(carDtoList, response);
    }

    @Test
    public void deleteAllCars_success() {
        carServiceImpl.deleteAllCars();
    }

    @Test
    public void deleteCarById_success() throws ResourceNotFoundException {
        long id = 5L;
        when(carRepositoryMock.findById(id)).thenReturn(Optional.empty());

        Exception e = assertThrows(ResourceNotFoundException.class, () -> {
            carServiceImpl.deleteCarById(id);
        });

        assertEquals(EXCEPTION + id, e.getMessage());
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

    private WeightDto buildWeightDto(Long id, String type) {
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
