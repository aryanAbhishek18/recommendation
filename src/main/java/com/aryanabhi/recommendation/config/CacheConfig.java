package com.aryanabhi.recommendation.config;

import com.aryanabhi.recommendation.entity.Car;
import com.aryanabhi.recommendation.repository.CarRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

import static com.aryanabhi.recommendation.Constants.CACHE_CLEAR_INITIAL_DELAY;
import static com.aryanabhi.recommendation.Constants.CACHE_CLEAR_INTERVAL;
import static com.aryanabhi.recommendation.Constants.CAR_CACHE_NAME;

@Log4j2
@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig {

    private CacheManager cacheManager;
    private CarRepository carRepository;

    @Autowired
    CacheConfig(CacheManager cacheManager, CarRepository carRepository) {
        this.cacheManager = cacheManager;
        this.carRepository = carRepository;
    }

    @PostConstruct
    public void preloadCache() {
        log.debug("Initializing application cache...");
        Cache cache = cacheManager.getCache(CAR_CACHE_NAME);
        for(Car car: carRepository.findAll()) {
            cache.put(car.getId(), car);
        }
    }

    @Scheduled(timeUnit = TimeUnit.MINUTES, fixedRate = CACHE_CLEAR_INTERVAL, initialDelay = CACHE_CLEAR_INITIAL_DELAY)
    public void clearCache() {
        log.debug("Clearing all the cache...");
        cacheManager.getCacheNames().stream().forEach((String cache) -> {
            cacheManager.getCache(cache).clear();
        });
    }
}
