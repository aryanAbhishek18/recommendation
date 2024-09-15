package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.dto.WeightDto;
import com.aryanabhi.recommendation.entity.Car;
import com.aryanabhi.recommendation.repository.CarRepository;
import com.aryanabhi.recommendation.utility.ScoreUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Car> allFetchedCars = carRepository.findAll();
        return allFetchedCars.stream().map((Car c) -> modelMapper.map(c, CarDto.class)).toList();
    }

    @Override
    public CarDto getCar(Long id) {
        Car fetchedCar = carRepository.getReferenceById(id);
        return modelMapper.map(fetchedCar, CarDto.class);
    }

    @Override
    public List<CarDto> createCars(List<CarDto> carDtoList, List<WeightDto> weightDtoList) {
        List<CarDto> savedCars = new ArrayList<>();
        Map<String, WeightDto> typeToWeightDto = scoreUtility.mapTypeToWeightDto(weightDtoList);
        for(CarDto carDto: carDtoList) {
            Double carScore = calculateScore(carDto);
            Car car = modelMapper.map(carDto, Car.class);
            Double score = scoreUtility.calculateScore(carDto, typeToWeightDto.get(carDto.getType()));
            car.setScore(score);
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
