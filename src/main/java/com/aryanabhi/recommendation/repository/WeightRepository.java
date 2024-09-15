package com.aryanabhi.recommendation.repository;

import com.aryanabhi.recommendation.entity.Weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeightRepository extends JpaRepository<Weight, Long> {

}
