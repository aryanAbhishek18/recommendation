package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.ComparisonDto;
import com.aryanabhi.recommendation.entity.Car;
import com.aryanabhi.recommendation.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class ComparisonServiceImpl implements ComparisonService {

    CarRepository carRepository;

    @Autowired
    ComparisonServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public ComparisonDto compareCars(List<Long> ids) {
        List<Car> carsToBeCompared = new ArrayList<>();

        List<String> names = new ArrayList<>();
        List<String> types = new ArrayList<>();
        List<String> companies = new ArrayList<>();
        List<Integer> capacities = new ArrayList<>();
        List<Float> mileages = new ArrayList<>();
        List<Integer> years = new ArrayList<>();


        HashSet<String> uniqueTypes = new HashSet<>();
        HashSet<String> uniqueCompanies = new HashSet<>();
        HashSet<Integer> uniqueCapacities = new HashSet<>();
        HashSet<Float> uniqueMileages = new HashSet<>();
        HashSet<Integer> uniqueYears = new HashSet<>();

        for(Long id: ids) {
            Car c = carRepository.getReferenceById(id);
            if(c != null) carsToBeCompared.add(c);

            names.add(c.getName());
            types.add(c.getType());
            uniqueTypes.add(c.getType());
            companies.add(c.getCompany());
            uniqueCompanies.add(c.getCompany());
            capacities.add(c.getCapacity());
            uniqueCapacities.add(c.getCapacity());
            mileages.add((c.getMileage()));
            uniqueMileages.add(c.getMileage());
            years.add(c.getYear());
            uniqueYears.add(c.getYear());
        }

        return ComparisonDto.builder()
                .ids(ids)
                .names(names)
                .types(uniqueTypes.size() == 1 ? uniqueTypes.stream().toList() : types)
                .companies(uniqueCompanies.size() == 1 ? uniqueCompanies.stream().toList() : companies)
                .capacities(uniqueCapacities.size() == 1 ? uniqueCapacities.stream().toList() : capacities)
                .mileages(uniqueMileages.size() == 1 ? uniqueMileages.stream().toList() : mileages)
                .years(uniqueYears.size() == 1 ? uniqueYears.stream().toList() : years)
                .build();
    }
}
