package com.aryanabhi.recommendation.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="weight", indexes = {@Index(name = "type_weight_index",  columnList="type")})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Weight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false, unique = true)
    private String type;

    @Column(name = "year", nullable = false)
    private Double year;

    @Column(name = "seating", nullable = false)
    private Double seating;

    @Column(name = "horsepower", nullable = false)
    private Double horsepower;

    @Column(name = "mileage", nullable = false)
    private Double mileage;

    @Column(name = "price", nullable = false)
    private Double price;
}