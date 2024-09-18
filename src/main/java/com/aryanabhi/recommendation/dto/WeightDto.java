package com.aryanabhi.recommendation.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class WeightDto {
    private Long id;
    private String type;
    private Double year;
    private Double seating;
    private Double horsepower;
    private Double mileage;
    private Double price;
}
