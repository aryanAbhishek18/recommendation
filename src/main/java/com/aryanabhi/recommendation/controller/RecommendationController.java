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
                                                      @RequestParam Optional<Integer> limit) {
        log.debug("Request to recommend cars for id: {}", id);
        if(isLimitInvalid(limit)) return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
        int recommendationsLimit = (limit != null && limit.isPresent()) ? limit.get() : CAR_RECOMMENDATION_DEFAULT_LIMIT;
        try {
            List<CarDto> recommendedCars = recommendationService.getRecommendations(id, recommendationsLimit);
            return ResponseEntity.ok(recommendedCars);
        } catch(ResourceNotFoundException e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    private boolean isLimitInvalid(Optional<Integer> limit) {
        if(limit != null && limit.isPresent() && limit.get() <= 0) return true;
        return false;
    }
}
