package com.aryanabhi.recommendation.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
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
