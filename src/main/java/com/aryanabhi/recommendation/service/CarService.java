package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.CarDto;

import java.util.List;

public interface CarService {

    List<CarDto> getAllCars();
    CarDto getCar(Long id);
    CarDto createCar(CarDto carDto);
    void deleteAllCars();
    void deleteCarById(Long id);
}
