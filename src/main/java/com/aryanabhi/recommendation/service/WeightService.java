package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.WeightDto;
import com.aryanabhi.recommendation.exception.ResourceNotFoundException;

import java.util.List;

public interface WeightService {

    List<WeightDto> createRankWeights(List<WeightDto> weightDtoList);
    List<WeightDto> getRankWeights();
    WeightDto getRankWeightByType(String type) throws ResourceNotFoundException;
    void deleteRankWeights();
}
