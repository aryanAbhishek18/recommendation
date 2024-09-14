package com.aryanabhi.recommendation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="car",
        indexes = {@Index(name = "type_index",  columnList="type")})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @Column(name = "seats", nullable = false)
    private Integer seats;

    @Column(name = "horsepower", nullable = false)
    private Double horsepower;

    @Column(name = "is_automatic", nullable = false)
    private Boolean isAutomatic;

    @Column(name = "mileage", nullable = false)
    private Double mileage;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "score", nullable = false)
    private Double score;
}
