package com.aryanabhi.recommendation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="weight")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Weight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false, unique = true)
    private String type;

    @Column(name = "seats", nullable = false)
    private Double seats;

    @Column(name = "mileage", nullable = false)
    private Double mileage;

    @Column(name = "horsepower", nullable = false)
    private Double horsepower;

    @Column(name = "is_automatic", nullable = false)
    private Double isAutomatic;
}
