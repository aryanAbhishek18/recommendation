package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.entity.Car;
import com.aryanabhi.recommendation.exception.ResourceNotFoundException;
import com.aryanabhi.recommendation.repository.CarRepository;
import com.aryanabhi.recommendation.utility.ScoreUtility;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.aryanabhi.recommendation.Constants.TYPE_CACHE_NAME;

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
    public List<CarDto> getRecommendations(Long id, Integer limit) throws ResourceNotFoundException {
        log.debug("Generating recommendations for car with id: {} ...", id);
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No car exists for id: " + id));
        Optional<List<Car>> sameTypeCars = getCarsByType(car.getType());
        if(sameTypeCars.isPresent() && sameTypeCars.get().size() > 1) {
            List<CarDto> carDtoList = sameTypeCars.get().stream().map(c -> modelMapper.map(c, CarDto.class)).toList();
            return scoreUtility.findMostRecommendedForScore(id, car.getScore(), carDtoList,
                    limit);
        }
        log.debug("No recommendations for car with id: {}", id);
        return null;
    }

    @Cacheable(value = TYPE_CACHE_NAME, key = "#type")
    private Optional<List<Car>> getCarsByType(String type) {
        return carRepository.findCarsByType(type);
    }
}
