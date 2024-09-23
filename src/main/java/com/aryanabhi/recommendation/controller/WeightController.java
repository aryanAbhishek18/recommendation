package com.aryanabhi.recommendation.controller;

import com.aryanabhi.recommendation.dto.WeightDto;
import com.aryanabhi.recommendation.exception.ResourceNotFoundException;
import com.aryanabhi.recommendation.service.WeightService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.aryanabhi.recommendation.Constants.WEIGHT_API_BASE_URL;

@Log4j2
@RestController
@RequestMapping(path = WEIGHT_API_BASE_URL)
public class WeightController {

    private final WeightService weightService;

    @Autowired
    WeightController(WeightService weightService) {
        this.weightService = weightService;
    }

    @PostMapping
    ResponseEntity<List<WeightDto>> createWeights(@RequestBody List<WeightDto> weightDtos) {
        log.debug("Request to create weights for scoring algorithm");
        List<WeightDto> fetchedWeights =  weightService.createRankWeights(weightDtos);
        if(fetchedWeights == null) return new ResponseEntity<>(null, HttpStatus.PRECONDITION_FAILED);
        return new ResponseEntity<>(fetchedWeights, HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<List<WeightDto>> getWeights() {
        log.debug("Request to fetch all weights");
        List<WeightDto> fetchedWeights = weightService.getRankWeights();
        return new ResponseEntity<>(fetchedWeights, HttpStatus.OK);
    }

    @GetMapping(path = "/{type}")
    ResponseEntity<WeightDto> getWeightByType(@PathVariable String type) {
        log.debug("Request to fetch weights with type: {}", type);
        try {
            WeightDto fetchedWeight = weightService.getRankWeightByType(type);
            return new ResponseEntity<>(fetchedWeight, HttpStatus.OK);
        } catch(ResourceNotFoundException e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    ResponseEntity<String> deleteWeights() {
        log.debug("Request to delete all weights");
        weightService.deleteRankWeights();
        return ResponseEntity.ok("Deleted all weight records!");
    }
}
