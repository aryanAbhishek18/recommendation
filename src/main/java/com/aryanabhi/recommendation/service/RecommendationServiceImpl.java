package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.entity.Car;
import com.aryanabhi.recommendation.repository.CarRepository;
import com.aryanabhi.recommendation.utility.ScoreUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.aryanabhi.recommendation.Constants.CAR_RECOMMENDATION_DEFAULT_LIMIT;

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
        Car car = carRepository.getReferenceById(id);
        Optional<List<Car>> sameTypeCars = carRepository.findCarsByType(car.getType());
        if(sameTypeCars.isPresent()) {
            List<CarDto> carDtoList = sameTypeCars.get().stream().map(c -> modelMapper.map(c, CarDto.class)).toList();
            return  scoreUtility.findMostRecommendedForScore(id, car.getScore(), carDtoList,
                    CAR_RECOMMENDATION_DEFAULT_LIMIT);
        }
        return null;
    }
}
