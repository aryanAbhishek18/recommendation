package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.exception.ResourceNotFoundException;

import java.util.List;

public interface RecommendationService {

    List<CarDto> getRecommendations(Long id, Integer limit) throws ResourceNotFoundException;
}
