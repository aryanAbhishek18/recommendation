package com.aryanabhi.recommendation.utility;

import com.aryanabhi.recommendation.dto.CarDto;
import com.aryanabhi.recommendation.dto.WeightDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ScoreUtility {

    /**
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

    public Map<String, WeightDto> mapTypeToWeightDto(List<WeightDto> weightDtoList) {
        Map<String, WeightDto> typeToWeight = new HashMap<>();
        for(WeightDto weightDto: weightDtoList) {
            typeToWeight.put(weightDto.getType(), weightDto);
        }
        return typeToWeight;
    }
}
