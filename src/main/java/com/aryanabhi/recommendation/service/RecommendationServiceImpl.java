package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.entity.Car;
import com.aryanabhi.recommendation.exceptions.ResourceNotFoundException;
import com.aryanabhi.recommendation.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.aryanabhi.recommendation.Constants.NUMBER_OF_RECOMMENDATIONS;
import static com.aryanabhi.recommendation.Constants.PAGE_SIZE;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private CarRepository carRepository;
    private ModelMapper modelMapper;
    private List<CarDto> carsByScore;
    // Key is CarID, Value is Index of Car in "carsByScore"
    private Map<Long, Integer> carIndexes;

    @Override
    public List<CarDto> getRecommendations(Long carId) {
        // First check if the car exists or not
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car with ID : " + carId + "does not exist."));

        // Find the index of the car in "carsByScore" list
        int index = carIndexes.get(carId);

        // To store the recommended cars
        List<CarDto> recommendedCars = new ArrayList<>();
        
        int delta = 1;
        while (index - delta >= 0 && index + delta < carsByScore.size()
                && recommendedCars.size() < NUMBER_OF_RECOMMENDATIONS) {

            CarDto leftCar = carsByScore.get(index - delta);
            CarDto rightCar = carsByScore.get(index + delta);

            double leftScoreDiff = Math.abs(leftCar.getScore() - carsByScore.get(index).getScore());
            double rightScoreDiff = Math.abs(rightCar.getScore() - carsByScore.get(index).getScore());

            if (leftScoreDiff > rightScoreDiff) {
                recommendedCars.add(rightCar);
            }
            else {
                recommendedCars.add(leftCar);
            }

            delta++;
        }

        if (recommendedCars.size() < NUMBER_OF_RECOMMENDATIONS) {
            if (index - delta >= 0) {
                while (recommendedCars.size() < NUMBER_OF_RECOMMENDATIONS) {
                    recommendedCars.add(carsByScore.get(index - delta));
                    delta++;
                }
            }
            else if (index + delta < carsByScore.size()) {
                while (recommendedCars.size() < NUMBER_OF_RECOMMENDATIONS) {
                    recommendedCars.add(carsByScore.get(index + delta));
                    delta++;
                }
            }
        }

        return recommendedCars;
    }

    @Override
    public void getAllCars() {
        // Will store all the cars
        List<Car> allCars = new ArrayList<>();

        // Get all cars in paginated way
        int pageNo = 0;
        int totalPages;
        do {
            Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE);
            Page<Car> carsPage = carRepository.findAll(pageable);
            allCars.addAll(carsPage.getContent());
            totalPages = carsPage.getTotalPages();
            pageNo++;
        } while (pageNo < totalPages);

        // Convert all Car.java to CarDto.java
        for (Car car : allCars) {
            CarDto carDto = modelMapper.map(car, CarDto.class);
            carsByScore.add(carDto);
        }

        // Sort all the cars by score
        carsByScore.sort(Comparator.comparing(CarDto::getScore));

        // Store indices of all the cars in a map
        for (int i = 0; i < carsByScore.size(); i++) {
            carIndexes.put(carsByScore.get(i).getId(), i);
        }
    }


}
