package com.aryanabhi.recommendation.controller;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.exception.ResourceNotFoundException;
import com.aryanabhi.recommendation.service.RecommendationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.aryanabhi.recommendation.Constants.CAR_RECOMMEND_API;

@Log4j2
@RestController
@RequestMapping(path=CAR_RECOMMEND_API)
public class RecommendationController {

    RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    /**
     * Recommendation endpoints
     */
    @GetMapping(path="/{id}")
    public ResponseEntity<List<CarDto>> recommendCars(@PathVariable(name = "id") Long id) {
        log.debug("Request to recommend cars for id: {}", id);
        try {
            List<CarDto> recommendedCars = recommendationService.getRecommendations(id);
            return ResponseEntity.ok(recommendedCars);
        } catch(ResourceNotFoundException e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
