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

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "mileage", nullable = false)
    private Float mileage;

    @Column(name = "year", nullable = false)
    private Integer year;
}
