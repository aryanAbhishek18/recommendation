package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.entity.Car;
import com.aryanabhi.recommendation.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<CarDto> createCars(List<CarDto> carDtoList) {
        List<CarDto> savedCars = new ArrayList<>();
        for(CarDto carDto: carDtoList) {
            Double carScore = calculateScore(carDto);
            Car car = modelMapper.map(carDto, Car.class);
            car.setScore(carScore);
            Car savedCar = carRepository.save(car);
            savedCars.add(modelMapper.map(savedCar, CarDto.class));
        }
        return savedCars;
    }

    @Override
    public void deleteAllCars() {
        carRepository.deleteAll();
    }

    @Override
    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }

    private Double calculateScore(CarDto carDto) {
        // write the actual algorithm to calculate score
        return 1.0;
    }
}
