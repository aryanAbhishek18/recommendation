package com.aryanabhi.recommendation.controller;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.exception.InvalidInputException;
import com.aryanabhi.recommendation.exception.ResourceNotFoundException;
import com.aryanabhi.recommendation.service.RecommendationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static com.aryanabhi.recommendation.Constants.CAR_RECOMMENDATION_DEFAULT_LIMIT;
import static com.aryanabhi.recommendation.Constants.CAR_RECOMMENDATION_URL;

@Log4j2
@RestController
@RequestMapping(path = CAR_RECOMMENDATION_URL)
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<List<CarDto>> recommendCars(@PathVariable(name = "id") Long id,
                                                      @RequestParam Optional<Integer> limit)
            throws ResourceNotFoundException, InvalidInputException {
        log.debug("Request to recommend cars for id: {}", id);
        int recommendationsLimit = (limit != null && limit.isPresent()) ? limit.get() : CAR_RECOMMENDATION_DEFAULT_LIMIT;
        List<CarDto> recommendedCars = recommendationService.getRecommendations(id, recommendationsLimit);
        return ResponseEntity.ok(recommendedCars);
    }
}
