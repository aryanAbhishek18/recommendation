package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.dto.WeightDto;
import com.aryanabhi.recommendation.exception.ResourceNotFoundException;

import java.util.List;

public interface CarService {

    List<CarDto> getAllCars();
    CarDto getCar(Long id) throws ResourceNotFoundException;
    List<CarDto> createCars(List<CarDto> carDtoList, List<WeightDto> weightDtoList);
    void deleteAllCars();
    void deleteCarById(Long id)  throws ResourceNotFoundException;;
}
