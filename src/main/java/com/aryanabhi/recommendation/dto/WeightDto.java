package com.aryanabhi.recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WeightDto {
    private Long id;
    private String type;
    private Double year;
    private Double seating;
    private Double horsepower;
    private Double mileage;
    private Double price;
}
