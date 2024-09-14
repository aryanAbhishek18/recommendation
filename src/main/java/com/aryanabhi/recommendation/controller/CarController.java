package com.aryanabhi.recommendation.controller;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.dto.ComparisonDto;
import com.aryanabhi.recommendation.service.CarServiceImpl;
import com.aryanabhi.recommendation.service.ComparisonServiceImpl;
import com.aryanabhi.recommendation.service.RecommendationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/car")
public class CarController {

    CarServiceImpl carServiceImpl;
    RecommendationServiceImpl recommendationServiceImpl;
    ComparisonServiceImpl comparisonServiceImpl;

    @Autowired
    public CarController(CarServiceImpl carServiceImpl, RecommendationServiceImpl recommendationServiceImpl, ComparisonServiceImpl comparisonServiceImpl) {
        this.carServiceImpl = carServiceImpl;
        this.recommendationServiceImpl = recommendationServiceImpl;
        this.comparisonServiceImpl = comparisonServiceImpl;
    }

    /**
     * Custom health check endpoint:
     * NOTE - see if inbuilt health check exists
     */
    @GetMapping(path="/health")
    public ResponseEntity<String> checkHealth() {
        System.out.println("Received health check request:))");
        return ResponseEntity.ok("Up and running!");
    }


    /**
     * Car create, delete and fetch endpoints:
     */
    @GetMapping(path="")
    public ResponseEntity<List<CarDto>> fetchAllCard() {
        System.out.println("Received all cars fetch request");
        try {
            List<CarDto> allCars = carServiceImpl.getAllCars();
            System.out.println("Total cars fetched: " + allCars.size());
            return ResponseEntity.ok(allCars);
        }
        catch (Exception e) {
            System.out.println("No cars found");
            return null;
        }
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<CarDto> fetchCarById(@PathVariable(name = "id") Long id) {
        System.out.println("Received car fetch request for id: " + id);
        try {
            CarDto fetchedCar = carServiceImpl.getCar(id);
            System.out.println("Response from car service: " + fetchedCar.getName());
            return ResponseEntity.ok(fetchedCar);
        }
        catch (Exception e) {
            System.out.println("No car found for the id: " + id);
            return null;
        }
    }

    @PostMapping(path="/create")
    public ResponseEntity<CarDto> createCar(@RequestBody CarDto carDto) {
        System.out.println("Received car create request for: " + carDto);
        CarDto savedCar = carServiceImpl.createCar(carDto);
        System.out.println("Response from car service: " + savedCar);
        return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
    }

    @DeleteMapping(path="")
    public ResponseEntity<String> deleteAllCars() {
        System.out.println("Received all cars delete request");
        carServiceImpl.deleteAllCars();
        return ResponseEntity.ok("Deleted all cars!");
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<String> deleteCarById(@PathVariable(name = "id") Long id) {
        System.out.println("Received car delete request for id: " + id);
        carServiceImpl.deleteCarById(id);
        return ResponseEntity.ok("Deleted car with id: " + id);
    }


    /**
     * Recommendation endpoints
     */
    @GetMapping(path="/recommend/{id}")
    public ResponseEntity<List<CarDto>> recommendCars(@PathVariable(name = "id") Long id) {
        System.out.println("Received car recommendations request for id: " + id);
        try {
            List<CarDto> recommendedCars = recommendationServiceImpl.getRecommendations(id);
            System.out.println("Recommendations fetched: " + recommendedCars.size());
            return ResponseEntity.ok(recommendedCars);
        }
        catch (Exception e) {
            System.out.println("No recommendations found for the id: " + id);
            return null;
        }
    }


    /**
     * Comparison endpoints:
     */
    @GetMapping(path="/compare")
    public ResponseEntity<ComparisonDto> compareCars(@RequestBody List<Long> ids) {
        System.out.println("Received car compare request for: " + ids);
        ComparisonDto comparison = comparisonServiceImpl.compareCars(ids);
        System.out.println("Recommendations fetched: " + comparison);
        return new ResponseEntity<>(comparison, HttpStatus.OK);
    }
}
