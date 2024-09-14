package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.CarDto;

import java.util.List;

public interface RecommendationService {
    List<CarDto> getRecommendations(Long id);
    void getAllCars();
}
