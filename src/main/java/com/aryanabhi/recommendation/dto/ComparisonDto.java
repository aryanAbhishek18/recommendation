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
public class ComparisonDto {
    List<Long> ids;
    List<String> names;
    List<String> types;
    List<String> companies;
    List<Integer> capacities;
    List<Float> mileages;
    List<Integer> years;
}
