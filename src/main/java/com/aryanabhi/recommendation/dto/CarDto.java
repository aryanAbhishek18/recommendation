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
    private Integer seats;
    private Double horsepower;
    private Boolean isAutomatic;
    private Double mileage;
    private Integer year;
    private Double score;
}
