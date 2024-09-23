package com.aryanabhi.recommendation.controller;

import com.aryanabhi.recommendation.dto.ComparisonRequestDto;
import com.aryanabhi.recommendation.dto.ComparisonResponseDto;
import com.aryanabhi.recommendation.exception.ResourceNotFoundException;
import com.aryanabhi.recommendation.service.ComparisonService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.aryanabhi.recommendation.Constants.CAR_COMPARISON_URL;

@Log4j2
@RestController
@RequestMapping(path = CAR_COMPARISON_URL)
public class ComparisonController {

    private final ComparisonService comparisonService;

    @Autowired
    public ComparisonController(ComparisonService comparisonService) {
        this.comparisonService = comparisonService;
    }

    @PostMapping
    public ResponseEntity<ComparisonResponseDto> compareCars(@RequestBody ComparisonRequestDto req) {
        log.debug("Request to fetch comparisons for cars with ids: {}", req.getIds());
        try {
            ComparisonResponseDto comparison = comparisonService.compareCars(req);
            return new ResponseEntity<>(comparison, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
