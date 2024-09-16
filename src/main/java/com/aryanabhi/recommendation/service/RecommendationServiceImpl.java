package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.entity.Car;
import com.aryanabhi.recommendation.repository.CarRepository;
import com.aryanabhi.recommendation.utility.ScoreUtility;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.aryanabhi.recommendation.Constants.CAR_RECOMMENDATION_DEFAULT_LIMIT;

@Log4j2
@Service
public class RecommendationServiceImpl implements RecommendationService {

    CarRepository carRepository;
    ModelMapper modelMapper;
    ScoreUtility scoreUtility;

    @Autowired
    RecommendationServiceImpl(CarRepository carRepository, ModelMapper modelMapper, ScoreUtility scoreUtility) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.scoreUtility = scoreUtility;
    }

    @Override
    public List<CarDto> getRecommendations(Long id) {
        log.debug("Generating recommendations for car with id: {} ...", id);
        Car car = carRepository.getReferenceById(id);
        Optional<List<Car>> sameTypeCars = carRepository.findCarsByType(car.getType());
        if(sameTypeCars.isPresent() && sameTypeCars.get().size() > 1) {
            List<CarDto> carDtoList = sameTypeCars.get().stream().map(c -> modelMapper.map(c, CarDto.class)).toList();
            return scoreUtility.findMostRecommendedForScore(id, car.getScore(), carDtoList,
                    CAR_RECOMMENDATION_DEFAULT_LIMIT);
        }
        log.debug("No recommendations for car with id: {}", id);
        return null;
    }
}
