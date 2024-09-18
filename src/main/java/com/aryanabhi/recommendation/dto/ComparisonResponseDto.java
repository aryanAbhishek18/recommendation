package com.aryanabhi.recommendation.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ComparisonResponseDto {
    private List<Long> ids;
    private List<String> names;
    private List<String> types;
    private List<String> companies;
    private List<Integer> years;
    private List<Integer> seatings;
    private List<Boolean> automaticAvailibility;
    private List<Double> horsepowers;
    private List<Double> mileages;
    private List<Double> prices;
    private List<Double> scores;
}
