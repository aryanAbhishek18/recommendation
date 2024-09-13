package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.CarDto;

public interface CarService {

    public CarDto getCar(Long id);
    public CarDto createCar(CarDto carDto);
}
