package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.entity.Car;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RecommendationService {

    public List<Car> getRecommendations(int id) {
        // fetch from DB
        Car creta = new Car(1L, "CRETA", "SUV");
        return Arrays.asList(creta);
    }

}
