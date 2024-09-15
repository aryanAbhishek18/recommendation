package com.aryanabhi.recommendation.utility;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.dto.WeightDto;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ScoreUtility {

    /**
     * Utility method to calculate the score of a car based on its atttributes and thresholds
     *
     * @param carDto represents the car for which score is to be calculated
     * @param weightDto represents the weight thresholds for all the specifications
     * @return score of the provided car according to the weight thresholds
     */
    public Double calculateScore(CarDto carDto, WeightDto weightDto) {

        Double yearWeightage = carDto.getYear() * weightDto.getYear();
        Double seatingWeightage = carDto.getSeating() * weightDto.getSeating();
        Double hpWeightage = carDto.getHorsepower() * weightDto.getHorsepower();
        Double mileageWeightage = carDto.getMileage() * weightDto.getMileage();
        Double priceWeightage = carDto.getPrice() * weightDto.getPrice();

        return yearWeightage + seatingWeightage + hpWeightage + mileageWeightage + priceWeightage;
    }

    /**
     * Utility method to create a mapping of Car type to the category weights thresholds
     *
     * @param weightDtoList represents all the weights records
     * @return map of car type to attribute weights
     */
    public Map<String, WeightDto> mapTypeToWeightDto(List<WeightDto> weightDtoList) {
        Map<String, WeightDto> typeToWeight = new HashMap<>();
        for(WeightDto weightDto: weightDtoList) {
            typeToWeight.put(weightDto.getType(), weightDto);
        }
        return typeToWeight;
    }

    /**
     *Utility method to find the most recommended cars
     *
     * @param score represents score of the car for which recommendations have to be fetched
     * @param carDtoList represents all the cars of same type
     * @param limit represents the max number of recommendations to be returned
     * @return a max of specified number of most recommended cars
     */
    public List<CarDto> findMostRecommendedForScore(Long id, Double score, List<CarDto> carDtoList, int limit) {
        List<CarDto> mostRecommendedCars = new ArrayList<>(carDtoList);
        Collections.sort(mostRecommendedCars, (CarDto car1, CarDto car2) -> {
            Double absDiff1 = Math.abs(score - car1.getScore());
            Double absDiff2 = Math.abs(score - car2.getScore());
            return Double.compare(absDiff1, absDiff2);
        });
        return mostRecommendedCars.stream().filter(c -> !c.getId().equals(id)).limit(limit).toList();
    }
}
