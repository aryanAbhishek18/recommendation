package com.aryanabhi.recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarDto {
    private Long id;
    private String name;
    private String type;
    private String company;
    private Integer year;
    private Integer seating;
    private Boolean automaticAvailable;
    private Double horsepower;
    private Double mileage;
    private Double price;
    private Double score;
}
