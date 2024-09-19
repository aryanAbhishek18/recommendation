package com.aryanabhi.recommendation.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="car",
        indexes = {@Index(name = "type_index",  columnList="type")})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "company", nullable = false)
    private String company;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "seating", nullable = false)
    private Integer seating;

    @Column(name = "automatic_available", nullable = false)
    private Boolean automaticAvailable;

    @Column(name = "horsepower", nullable = false)
    private Double horsepower;

    @Column(name = "mileage", nullable = false)
    private Double mileage;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "score", nullable = false)
    private Double score;
}
