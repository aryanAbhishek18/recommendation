package com.aryanabhi.recommendation.controller;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.service.CarService;
import com.aryanabhi.recommendation.service.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.aryanabhi.recommendation.Constants.CAR_API;
import static com.aryanabhi.recommendation.Constants.HEALTH_CHECK_API;

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
        System.out.println("Received health check request:))");
        return ResponseEntity.ok("Up and running!");
    }


    /**
     * Car create, delete and fetch endpoints:
     */
    @GetMapping(path="")
    public ResponseEntity<List<CarDto>> fetchAllCars() {
        System.out.println("Received all cars fetch request");
        try {
            List<CarDto> allCars = carService.getAllCars();
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
            CarDto fetchedCar = carService.getCar(id);
            System.out.println("Response from car service: " + fetchedCar.getName());
            return ResponseEntity.ok(fetchedCar);
        }
        catch (Exception e) {
            System.out.println("No car found for the id: " + id);
            return null;
        }
    }

    @PostMapping(path="")
    public ResponseEntity<List<CarDto>> createCars(@RequestBody List<CarDto> carDtoList) {
        List<CarDto> savedCars = carService.createCars(carDtoList, weightService.getRankWeights());
        return new ResponseEntity<>(savedCars, HttpStatus.CREATED);
    }

    @DeleteMapping(path="")
    public ResponseEntity<String> deleteAllCars() {
        System.out.println("Received all cars delete request");
        carService.deleteAllCars();
        return ResponseEntity.ok("Deleted all cars!");
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<String> deleteCarById(@PathVariable(name = "id") Long id) {
        System.out.println("Received car delete request for id: " + id);
        carService.deleteCarById(id);
        return ResponseEntity.ok("Deleted car with id: " + id);
    }
}
