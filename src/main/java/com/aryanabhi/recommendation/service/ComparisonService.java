package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.ComparisonResponseDto;
import com.aryanabhi.recommendation.dto.ComparisonRequestDto;

public interface ComparisonService {
    ComparisonResponseDto compareCars(ComparisonRequestDto comparisonRequestDto);
}
