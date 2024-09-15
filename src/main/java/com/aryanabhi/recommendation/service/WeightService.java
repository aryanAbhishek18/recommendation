package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.WeightDto;

import java.util.List;

public interface WeightService {

    List<WeightDto> createRankWeights(List<WeightDto> weightDtoList);
    List<WeightDto> getRankWeights();
    void deleteRankWeights();
}
