package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.dto.WeightDto;
import com.aryanabhi.recommendation.entity.Car;
import com.aryanabhi.recommendation.repository.CarRepository;
import com.aryanabhi.recommendation.utility.ScoreUtility;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class CarServiceImpl implements CarService {

    CarRepository carRepository;
    ModelMapper modelMapper;
    ScoreUtility scoreUtility;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, ScoreUtility scoreUtility) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.scoreUtility = scoreUtility;
    }

    @Override
    public List<CarDto> getAllCars() {
        log.debug("Fetching all cars...");
        List<Car> allFetchedCars = carRepository.findAll();
        log.debug("Fetched a total of {} cars", allFetchedCars.size());
        return allFetchedCars.stream().map((Car c) -> modelMapper.map(c, CarDto.class)).toList();
    }

    @Override
    public CarDto getCar(Long id) {
        log.debug("Fetching car with id: {} ...", id);
        Car fetchedCar = carRepository.getReferenceById(id);
        log.debug("Fetched car with id: {}", id);
        return modelMapper.map(fetchedCar, CarDto.class);
    }

    @Override
    public List<CarDto> createCars(List<CarDto> carDtoList, List<WeightDto> weightDtoList) {
        log.debug("Creating cars...");
        List<CarDto> savedCars = new ArrayList<>();
        Map<String, WeightDto> typeToWeightDto = scoreUtility.mapTypeToWeightDto(weightDtoList);
        for(CarDto carDto: carDtoList) {
            Car car = modelMapper.map(carDto, Car.class);
            Double score = scoreUtility.calculateScore(carDto, typeToWeightDto.get(carDto.getType()));
            log.debug("Calculated score - {} for {}", score, car.getName());
            car.setScore(score);
            Car savedCar = carRepository.save(car);
            log.debug("Saved new car with id: {}", savedCar.getId());
            savedCars.add(modelMapper.map(savedCar, CarDto.class));
        }
        log.debug("Saved a total of {} cars", savedCars.size());
        return savedCars;
    }

    @Override
    public void deleteAllCars() {
        log.debug("Deleting all cars...");
        carRepository.deleteAll();
        log.debug("Deleted all cars");
    }

    @Override
    public void deleteCarById(Long id) {
        log.debug("Deleting car with id: {} ...", id);
        carRepository.deleteById(id);
        log.debug("Deleted car with id: {}", id);
    }
}
