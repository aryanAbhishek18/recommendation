package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.ComparisonResponseDto;
import com.aryanabhi.recommendation.dto.ComparisonRequestDto;
import com.aryanabhi.recommendation.exception.ResourceNotFoundException;

public interface ComparisonService {
    ComparisonResponseDto compareCars(ComparisonRequestDto comparisonRequestDto) throws ResourceNotFoundException;
}
