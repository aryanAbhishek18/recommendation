package com.aryanabhi.recommendation.controller;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/api/car/recommend")
public class RecommendationController {

    RecommendationService recommendationServiceImpl;

    @Autowired
    public RecommendationController(RecommendationService recommendationServiceImpl) {
        this.recommendationServiceImpl = recommendationServiceImpl;
    }

    /**
     * Recommendation endpoints
     */
    @GetMapping(path="/{id}")
    public ResponseEntity<List<CarDto>> recommendCars(@PathVariable(name = "id") Long id) {
        System.out.println("Received car recommendations request for id: " + id);
        try {
            List<CarDto> recommendedCars = recommendationServiceImpl.getRecommendations(id);
            System.out.println("Recommendations fetched: " + recommendedCars.size());
            return ResponseEntity.ok(recommendedCars);
        }
        catch (Exception e) {
            System.out.println("No recommendations found for the id: " + id);
            return null;
        }
    }
}
