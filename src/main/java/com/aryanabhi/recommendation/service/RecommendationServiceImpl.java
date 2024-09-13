package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.CarDto;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Override
    public List<CarDto> getRecommendations(Long id) {
        return Arrays.asList();
    }
}
