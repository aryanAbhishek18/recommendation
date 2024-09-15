package com.aryanabhi.recommendation.repository;

import com.aryanabhi.recommendation.entity.Weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeightRepository extends JpaRepository<Weight, Long> {

    @Query("SELECT u FROM Weight u WHERE u.type = :type")
    Optional<Weight> findOneByType(@Param("type") String type);
}
