package com.aryanabhi.recommendation.controller;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.service.CarService;
import com.aryanabhi.recommendation.service.RecommendationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.aryanabhi.recommendation.Constants.*;

@RestController
@AllArgsConstructor
@RequestMapping(path=CAR_API)
public class CarController {

    private RecommendationServiceImpl recommendationServiceImpl;
    private CarService carService;

    /**
     * Custom health check endpoint
     * NOTE - see if inbuilt health check exists
     */
    @GetMapping(path=HEALTH_API)
    public ResponseEntity<String> checkHealth() {
        System.out.println("Received health check request:))");
        return ResponseEntity.ok("Up and running!");
    }


    /**
     * Car create, delete and fetch endpoints:
     */
    @GetMapping(path=GET_ALL_CARS_API)
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

    @PostMapping(path=CREATE_CAR_API)
    public ResponseEntity<CarDto> createCar(@RequestBody CarDto carDto) {
        System.out.println("Received car create request for: " + carDto);
        CarDto savedCar = carService.createCar(carDto);
        System.out.println("Response from car service: " + savedCar);
        return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
    }

    @DeleteMapping(path= DELETE_ALL_CARS_API)
    public ResponseEntity<String> deleteAllCars() {
        System.out.println("Received all cars delete request");
        carService.deleteAllCars();
        return ResponseEntity.ok("Deleted all cars!");
    }

    @DeleteMapping(path=DELETE_CAR_API)
    public ResponseEntity<String> deleteCarById(@PathVariable(name = "id") Long id) {
        System.out.println("Received car delete request for id: " + id);
        carService.deleteCarById(id);
        return ResponseEntity.ok("Deleted car with id: " + id);
    }


    /**
     * Recommendation endpoints
     */
    @GetMapping(path=RECOMMEND_API)
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
}
