package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.ComparisonResponseDto;
import com.aryanabhi.recommendation.dto.ComparisonRequestDto;
import com.aryanabhi.recommendation.entity.Car;
import com.aryanabhi.recommendation.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ComparisonServiceImpl implements ComparisonService {

    CarRepository carRepository;

    @Autowired
    ComparisonServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public ComparisonResponseDto compareCars(ComparisonRequestDto comparisonRequestDto) {
        List<Car> carsToBeCompared = new ArrayList<>();

        List<String> names = new ArrayList<>();
        List<String> types = new ArrayList<>();
        List<String> companies = new ArrayList<>();
        List<Integer> years = new ArrayList<>();
        List<Integer> seatings = new ArrayList<>();
        List<Boolean> automaticAvailability = new ArrayList<>();
        List<Double> horsepowers = new ArrayList<>();
        List<Double> mileages = new ArrayList<>();
        List<Double> prices = new ArrayList<>();
        List<Double> scores = new ArrayList<>();

        Set<String> uniqueTypes = new HashSet<>();
        Set<String> uniqueCompanies = new HashSet<>();
        Set<Integer> uniqueYears = new HashSet<>();
        Set<Integer> uniqueSeatings = new HashSet<>();
        Set<Boolean> uniqueAutomaticAvailability = new HashSet<>();
        Set<Double> uniqueHorsepowers = new HashSet<>();
        Set<Double> uniqueMileages = new HashSet<>();
        Set<Double> uniquePrices = new HashSet<>();
        Set<Double> uniqueScores = new HashSet<>();


        for(Long id: comparisonRequestDto.getIds()) {
            Car c = carRepository.getReferenceById(id);
            if(c != null) carsToBeCompared.add(c);

            names.add(c.getName());

            types.add(c.getType());
            uniqueTypes.add(c.getType());

            companies.add(c.getCompany());
            uniqueCompanies.add(c.getCompany());

            years.add(c.getYear());
            uniqueYears.add(c.getYear());

            seatings.add(c.getSeating());
            uniqueSeatings.add(c.getSeating());

            automaticAvailability.add(c.getAutomaticAvailable());
            uniqueAutomaticAvailability.add(c.getAutomaticAvailable());

            horsepowers.add(c.getHorsepower());
            uniqueHorsepowers.add(c.getHorsepower());

            mileages.add((c.getMileage()));
            uniqueMileages.add(c.getMileage());

            prices.add(c.getPrice());
            uniquePrices.add(c.getPrice());

            scores.add((c.getScore()));
            uniqueScores.add(c.getScore());
        }

        if(comparisonRequestDto.getHideSimilarities() != null && comparisonRequestDto.getHideSimilarities()) {
            return ComparisonResponseDto.builder()
                    .ids(comparisonRequestDto.getIds())
                    .names(names)
                    .types(uniqueTypes.size() == 1 ? null : types)
                    .companies(uniqueCompanies.size() == 1 ? null : companies)
                    .years(uniqueYears.size() == 1 ? null : years)
                    .seatings(uniqueSeatings.size() == 1 ? null : seatings)
                    .automaticAvailibility(uniqueAutomaticAvailability.size() == 1 ? null : automaticAvailability)
                    .horsepowers(uniqueHorsepowers.size() == 1 ? null : horsepowers)
                    .mileages(uniqueMileages.size() == 1 ? null : mileages)
                    .prices(uniquePrices.size() == 1 ? null : prices)
                    .scores(uniqueScores.size() == 1 ? null : scores)
                    .build();
        }

        return ComparisonResponseDto.builder()
                .ids(comparisonRequestDto.getIds())
                .names(names)
                .types(uniqueTypes.size() == 1 ? uniqueTypes.stream().toList() : types)
                .companies(uniqueCompanies.size() == 1 ? uniqueCompanies.stream().toList() : companies)
                .years(uniqueYears.size() == 1 ? uniqueYears.stream().toList() : years)
                .seatings(uniqueSeatings.size() == 1 ? uniqueSeatings.stream().toList() : seatings)
                .automaticAvailibility(uniqueAutomaticAvailability.size() == 1 ? uniqueAutomaticAvailability.stream().toList() : automaticAvailability)
                .horsepowers(uniqueHorsepowers.size() == 1 ? uniqueHorsepowers.stream().toList() : horsepowers)
                .mileages(uniqueMileages.size() == 1 ? uniqueMileages.stream().toList() : mileages)
                .prices(uniquePrices.size() == 1 ? uniquePrices.stream().toList() : prices)
                .scores(uniqueScores.size() == 1 ? uniqueScores.stream().toList() : scores)
                .build();
    }
}
