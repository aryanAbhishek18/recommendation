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
    private Integer capacity;
    private Float mileage;
    private Integer year;
}
