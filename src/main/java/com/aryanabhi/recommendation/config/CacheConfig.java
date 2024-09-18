package com.aryanabhi.recommendation.config;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.service.CarService;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@EnableCaching
public class CacheConfig {

    private CacheManager cacheManager;
    private CarService carService;

    @Autowired
    CacheConfig(CacheManager cacheManager, CarService carService) {
        this.cacheManager = cacheManager;
        this.carService = carService;
    }

    @PostConstruct
    public void preloadCache() {
        log.debug("Initializing application cache...");
        Cache cache = cacheManager.getCache("carCache");
        for(CarDto car: carService.getAllCars()) {
            cache.put(car.getId(), car);
        }
    }
}
