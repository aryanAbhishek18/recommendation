package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.entity.Car;
import com.aryanabhi.recommendation.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    CarRepository carRepository;
    ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CarDto> getAllCars() {
        List<Car> allFetchedCars = carRepository.findAll();
        return allFetchedCars.stream().map((Car c) -> modelMapper.map(c, CarDto.class)).toList();
    }

    @Override
    public CarDto getCar(Long id) {
        Car fetchedCar = carRepository.getReferenceById(id);
        return modelMapper.map(fetchedCar, CarDto.class);
    }

    @Override
    public CarDto createCar(CarDto carDto) {
        // carDto.setScore() = func()

        Car car = modelMapper.map(carDto, Car.class);
        Car savedCar = carRepository.save(car);
        System.out.println("saved!");
        return modelMapper.map(savedCar, CarDto.class);
    }

    @Override
    public void deleteAllCars() {
        carRepository.deleteAll();
    }

    @Override
    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }
}
