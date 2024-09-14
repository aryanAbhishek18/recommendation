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
    List<Long> ids;
    List<String> names;
    List<String> types;
    List<String> companies;
    List<Integer> years;
    List<Integer> seatings;
    List<Boolean> automaticAvailibility;
    List<Double> horsepowers;
    List<Double> mileages;
    List<Double> prices;
    List<Double> scores;
}
