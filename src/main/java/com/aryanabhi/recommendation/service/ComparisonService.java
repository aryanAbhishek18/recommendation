package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.ComparisonDto;

import java.util.List;

public interface ComparisonService {
    ComparisonDto compareCars(List<Long> ids);
}
