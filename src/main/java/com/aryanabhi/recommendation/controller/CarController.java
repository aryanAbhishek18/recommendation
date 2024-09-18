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

import static com.aryanabhi.recommendation.Constants.CAR_API_BASE_URL;

@Log4j2
@RestController
@RequestMapping(path = CAR_API_BASE_URL)
public class CarController {

    private CarService carService;
    private WeightService weightService;

    @Autowired
    public CarController(CarService carService, WeightService weightService) {
        this.carService = carService;
        this.weightService = weightService;
    }

    @GetMapping
    public ResponseEntity<List<CarDto>> fetchAllCars() {
        log.debug("Request to fetch all cars");
        List<CarDto> allCars = carService.getAllCars();
        return ResponseEntity.ok(allCars);
    }

    @GetMapping(path = "/{id}")
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

    @PostMapping
    public ResponseEntity<List<CarDto>> createCars(@RequestBody List<CarDto> carDtoList) {
        log.debug("Request to create new cars");
        List<CarDto> savedCars = carService.createCars(carDtoList, weightService.getRankWeights());
        return new ResponseEntity<>(savedCars, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllCars() {
        log.debug("Request to delete all cars");
        carService.deleteAllCars();
        return ResponseEntity.ok("Deleted all cars!");
    }

    @DeleteMapping(path = "/{id}")
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
