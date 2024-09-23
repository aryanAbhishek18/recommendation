package com.aryanabhi.recommendation.service;

import com.aryanabhi.recommendation.dto.WeightDto;
import com.aryanabhi.recommendation.entity.Weight;
import com.aryanabhi.recommendation.exception.ResourceNotFoundException;
import com.aryanabhi.recommendation.repository.WeightRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class WeightServiceImpl implements WeightService {

    private final WeightRepository weightRepository;
    private final ModelMapper modelMapper;

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
    public WeightDto getRankWeightByType(String type) throws ResourceNotFoundException {
        log.debug("Fetching weights for type: {} ...", type);
        Weight weight = weightRepository.findOneByType(type);
        if(weight == null) throw new ResourceNotFoundException("No weight record exists for type: " + type);
        log.debug("Fetched weights for type: {}", type);
        return modelMapper.map(weight, WeightDto.class);
    }

    @Override
    public void deleteRankWeights() {
        log.debug("Deleting all weights...");
        weightRepository.deleteAll();
        log.debug("Deleted all weights");
    }
}
