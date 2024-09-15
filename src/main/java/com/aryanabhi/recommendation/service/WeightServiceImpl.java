package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.WeightDto;
import com.aryanabhi.recommendation.entity.Weight;
import com.aryanabhi.recommendation.repository.WeightRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WeightServiceImpl implements WeightService {

    WeightRepository weightRepository;
    ModelMapper modelMapper;

    @Autowired
    WeightServiceImpl(WeightRepository weightRepository, ModelMapper modelMapper) {
        this.weightRepository = weightRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<WeightDto> createRankWeights(List<WeightDto> weightDtoList) {
        System.out.println("create rank weight in weight service!");
        for(WeightDto weightDto: weightDtoList) {
            Weight weight = modelMapper.map(weightDto, Weight.class);

            try{
                weightRepository.save(weight);
            } catch (Exception e) {
                System.out.println("Duplicate entry: " + e.getMessage());
                return null;
            }
        }
        // check if we can .saveAll()

        List<Weight> savedWeights = weightRepository.findAll();
        List<WeightDto> savedWeightDtoList = new ArrayList<>();
        for(Weight w: savedWeights) {
            savedWeightDtoList.add(modelMapper.map(w, WeightDto.class));
        }
        return savedWeightDtoList;

    }

    @Override
    public List<WeightDto> getRankWeights() {
        List<Weight> weights = weightRepository.findAll();
        List<WeightDto> weightDtoList = new ArrayList<>();
        for(Weight w: weights) {
            weightDtoList.add(modelMapper.map(w, WeightDto.class));
        }
        return weightDtoList;
    }

    @Override
    public WeightDto getRankWeightByType(String type) {
        Optional<Weight> weight = weightRepository.findOneByType(type);
        if(weight.isEmpty()) return null;
        return modelMapper.map(weight.get(), WeightDto.class);
    }

    @Override
    public void deleteRankWeights() {
        weightRepository.deleteAll();
    }
}
