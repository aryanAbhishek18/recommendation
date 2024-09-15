package com.aryanabhi.recommendation.repository;

import com.aryanabhi.recommendation.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT u FROM Car u WHERE u.type = :type")
    Optional<List<Car>> findCarsByType(@Param("type") String type);
}
