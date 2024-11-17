package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.entity.Car;
import com.aryanabhi.recommendation.exception.InvalidInputException;
import com.aryanabhi.recommendation.exception.ResourceNotFoundException;
import com.aryanabhi.recommendation.repository.CarRepository;
import com.aryanabhi.recommendation.utility.ScoreUtility;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final ScoreUtility scoreUtility;

    @Autowired
    RecommendationServiceImpl(CarRepository carRepository, ModelMapper modelMapper, ScoreUtility scoreUtility) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.scoreUtility = scoreUtility;
    }

    @Override
    public List<CarDto> getRecommendations(Long id, Integer limit) throws InvalidInputException,ResourceNotFoundException {
        log.debug("Generating recommendations for car with id: {} ...", id);
        if(limit <= 0) throw new InvalidInputException("Limit should be positive!");
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No car exists for id: " + id));
        List<Car> sameTypeCars = carRepository.findByType(car.getType());
        if(!sameTypeCars.isEmpty()) {
            List<CarDto> carDtoList = sameTypeCars.stream().map(c -> modelMapper.map(c, CarDto.class)).toList();
            return scoreUtility.findMostRecommendedForScore(id, car.getScore(), carDtoList, limit);
        }
        log.debug("No recommendations for car with id: {}", id);
        return null;
    }
}
