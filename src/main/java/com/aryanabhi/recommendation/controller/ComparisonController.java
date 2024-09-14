package com.aryanabhi.recommendation.controller;

import com.aryanabhi.recommendation.dto.ComparisonRequestDto;
import com.aryanabhi.recommendation.dto.ComparisonResponseDto;
import com.aryanabhi.recommendation.service.ComparisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.aryanabhi.recommendation.Constants.CAR_COMPARE_API;

@RestController
@RequestMapping(path=CAR_COMPARE_API)
public class ComparisonController {

    ComparisonService comparisonServiceImpl;

    @Autowired
    public ComparisonController(ComparisonService comparisonServiceImpl) {
        this.comparisonServiceImpl = comparisonServiceImpl;
    }

    /**
     * Comparison endpoints:
     */
    @GetMapping(path="")
    public ResponseEntity<ComparisonResponseDto> compareCars(@RequestBody ComparisonRequestDto req) {
        System.out.println("Received car compare request for: " + req.getIds());
        ComparisonResponseDto comparison = comparisonServiceImpl.compareCars(req);
        System.out.println("Recommendations fetched: " + comparison);
        return new ResponseEntity<>(comparison, HttpStatus.OK);
    }
}
