package com.aryanabhi.recommendation.controller;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.exception.ResourceNotFoundException;
import com.aryanabhi.recommendation.service.CarService;
import com.aryanabhi.recommendation.service.WeightService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.aryanabhi.recommendation.Constants.CAR_API;
import static com.aryanabhi.recommendation.Constants.HEALTH_CHECK_API;

@Log4j2
@RestController
@RequestMapping(path=CAR_API)
public class CarController {

    CarService carService;
    WeightService weightService;

    @Autowired
    public CarController(CarService carService, WeightService weightService) {
        this.carService = carService;
        this.weightService = weightService;
    }

    /**
     * Custom health check endpoint:
     * NOTE - see if inbuilt health check exists
     */
    @GetMapping(path=HEALTH_CHECK_API)
    public ResponseEntity<String> checkHealth() {
        return ResponseEntity.ok("Up and running!");
    }


    /**
     * Car create, delete and fetch endpoints:
     */
    @GetMapping(path="")
    public ResponseEntity<List<CarDto>> fetchAllCars() {
        log.debug("Request to fetch all cars");
        try {
            List<CarDto> allCars = carService.getAllCars();
            return ResponseEntity.ok(allCars);
        }
        catch (Exception e) {
            return null;
        }
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<CarDto> fetchCarById(@PathVariable(name = "id") Long id) {
        log.debug("Request to fetch car with id: {}", id);
        try {
            CarDto fetchedCar = carService.getCar(id);
            return ResponseEntity.ok(fetchedCar);
        } catch (ResourceNotFoundException e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path="")
    public ResponseEntity<List<CarDto>> createCars(@RequestBody List<CarDto> carDtoList) {
        log.debug("Request to create new cars");
        List<CarDto> savedCars = carService.createCars(carDtoList, weightService.getRankWeights());
        return new ResponseEntity<>(savedCars, HttpStatus.CREATED);
    }

    @DeleteMapping(path="")
    public ResponseEntity<String> deleteAllCars() {
        log.debug("Request to delete all cars");
        carService.deleteAllCars();
        return ResponseEntity.ok("Deleted all cars!");
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<String> deleteCarById(@PathVariable(name = "id") Long id) {
        log.debug("Request to delete car with id: {}", id);
        try {
            carService.deleteCarById(id);
            return ResponseEntity.ok("Deleted car with id: " + id);
        } catch (ResourceNotFoundException e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
