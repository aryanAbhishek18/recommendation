package com.aryanabhi.recommendation.controller;

import com.aryanabhi.recommendation.dto.ComparisonRequestDto;
import com.aryanabhi.recommendation.dto.ComparisonResponseDto;
import com.aryanabhi.recommendation.service.ComparisonService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.aryanabhi.recommendation.Constants.CAR_COMPARE_API;

@Log4j2
@RestController
@RequestMapping(path=CAR_COMPARE_API)
public class ComparisonController {

    ComparisonService comparisonService;

    @Autowired
    public ComparisonController(ComparisonService comparisonService) {
        this.comparisonService = comparisonService;
    }

    /**
     * Comparison endpoints:
     */
    @GetMapping(path="")
    public ResponseEntity<ComparisonResponseDto> compareCars(@RequestBody ComparisonRequestDto req) {
        log.debug("Request to fetch comparisons for cars with ids: {}", req.getIds());
        ComparisonResponseDto comparison = comparisonService.compareCars(req);
        return new ResponseEntity<>(comparison, HttpStatus.OK);
    }
}
