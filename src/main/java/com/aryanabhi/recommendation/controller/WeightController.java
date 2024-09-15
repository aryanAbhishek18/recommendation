package com.aryanabhi.recommendation.controller;

import com.aryanabhi.recommendation.dto.WeightDto;
import com.aryanabhi.recommendation.service.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.aryanabhi.recommendation.Constants.CAR_RANK_WEIGHT_API;

@RestController
@RequestMapping(path=CAR_RANK_WEIGHT_API)
public class WeightController {

    WeightService weightServiceImpl;

    @Autowired
    WeightController(WeightService weightServiceImpl) {
        this.weightServiceImpl = weightServiceImpl;
    }

    /**
     * Ranking algorithm's attribute weights endpoints:
     */
    @PostMapping(path = "")
    ResponseEntity<List<WeightDto>> addWeights(@RequestBody List<WeightDto> weightDtos) {
        List<WeightDto> fetchedWeights =  weightServiceImpl.createRankWeights(weightDtos);
        if(fetchedWeights == null) return new ResponseEntity<>(null, HttpStatus.PRECONDITION_FAILED);
        return new ResponseEntity<>(fetchedWeights, HttpStatus.CREATED);
    }

    @GetMapping(path = "")
    ResponseEntity<List<WeightDto>> getWeights() {
        List<WeightDto> fetchedWeights = weightServiceImpl.getRankWeights();
        return new ResponseEntity<>(fetchedWeights, HttpStatus.OK);
    }

    @DeleteMapping(path = "")
    ResponseEntity<String> deleteWeights() {
        weightServiceImpl.deleteRankWeights();
        return ResponseEntity.ok("Deleted all weight records!");
    }
}
