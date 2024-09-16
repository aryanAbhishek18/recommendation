package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.WeightDto;
import com.aryanabhi.recommendation.entity.Weight;
import com.aryanabhi.recommendation.repository.WeightRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
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
        log.debug("Creating ranking weights...");
        for(WeightDto weightDto: weightDtoList) {
            Weight weight = modelMapper.map(weightDto, Weight.class);

            try{
                weightRepository.save(weight);
            } catch (Exception e) {
                log.error("Duplicate type found", e);
                return null;
            }
        }
        // check if we can .saveAll()

        List<Weight> savedWeights = weightRepository.findAll();
        List<WeightDto> savedWeightDtoList = new ArrayList<>();
        for(Weight w: savedWeights) {
            savedWeightDtoList.add(modelMapper.map(w, WeightDto.class));
        }
        log.debug("Saved a total of {} weight records", savedWeightDtoList);
        return savedWeightDtoList;

    }

    @Override
    public List<WeightDto> getRankWeights() {
        log.debug("Fetching all weight records...");
        List<Weight> weights = weightRepository.findAll();
        List<WeightDto> weightDtoList = new ArrayList<>();
        for(Weight w: weights) {
            weightDtoList.add(modelMapper.map(w, WeightDto.class));
        }
        log.debug("Fetched a total of {} weight records", weightDtoList.size());
        return weightDtoList;
    }

    @Override
    public WeightDto getRankWeightByType(String type) {
        log.debug("Fetching weights for type: {} ...", type);
        Optional<Weight> weight = weightRepository.findOneByType(type);
        if(weight.isEmpty()) return null;
        log.debug("Fetched weights for type: {}", type);
        return modelMapper.map(weight.get(), WeightDto.class);
    }

    @Override
    public void deleteRankWeights() {
        log.debug("Deleting all weights...");
        weightRepository.deleteAll();
        log.debug("Deleted all weights");
    }
}
