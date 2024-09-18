package com.aryanabhi.recommendation.repository;

import com.aryanabhi.recommendation.entity.Car;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.aryanabhi.recommendation.Constants.CAR_CACHE_NAME;
import static com.aryanabhi.recommendation.Constants.TYPE_CACHE_NAME;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Cacheable(value = CAR_CACHE_NAME, key = "#id")
    Optional<Car> findById(Long id);

    @Cacheable(value = TYPE_CACHE_NAME, key = "#type")
    List<Car> findByType(String type);
}
