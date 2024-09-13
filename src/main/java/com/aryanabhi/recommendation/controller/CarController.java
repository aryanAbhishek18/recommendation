package com.aryanabhi.recommendation.controller;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.service.CarServiceImpl;
import com.aryanabhi.recommendation.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/car")
public class CarController {

    RecommendationService recommendationService;
    CarServiceImpl carServiceImpl;

    @Autowired
    public CarController(RecommendationService recommendationService, CarServiceImpl carServiceImpl) {
        this.recommendationService = recommendationService;
        this.carServiceImpl = carServiceImpl;
    }

    // default health check exists so check that
    @GetMapping(path="/health")
    public ResponseEntity<String> checkHealth() {
        System.out.println("Received health check request:))");
        return ResponseEntity.ok("Up and running!");
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<String> fetchCarById(@PathVariable(name = "id") Long id) {
        System.out.println("Received car fetch request for id: " + id);
        CarDto car = carServiceImpl.getCar(id);
        System.out.println("Response from car service: " + car.getName());
        return ResponseEntity.ok(car.getName());
    }

    @PostMapping(path="/create")
    public ResponseEntity<CarDto> createCar(@RequestBody CarDto carDto) {
        System.out.println("Received car fetch request for: " + carDto);
        CarDto savedCar = carServiceImpl.createCar(carDto);
        System.out.println("Response from car service: " + savedCar);
        return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
    }
}
