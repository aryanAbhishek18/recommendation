package com.aryanabhi.recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WeightDto {
    private Long id;
    private String type;
    private Double capacity;
    private Double mileage;
    private Double horsepower;
    private Double isAutomatic;
}
