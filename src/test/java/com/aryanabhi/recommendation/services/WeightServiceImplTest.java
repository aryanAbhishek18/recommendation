package com.aryanabhi.recommendation.services;

import com.aryanabhi.recommendation.dto.WeightDto;
import com.aryanabhi.recommendation.entity.Weight;
import com.aryanabhi.recommendation.exception.ResourceNotFoundException;
import com.aryanabhi.recommendation.repository.WeightRepository;
import com.aryanabhi.recommendation.service.WeightServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeightServiceImplTest {

    @InjectMocks
    @Spy
    private WeightServiceImpl weightServiceImpl;

    @Mock
    private WeightRepository weightRepositoryMock;

    @Mock
    private ModelMapper modelMapperMock;

    private WeightDto weightDto1;
    private WeightDto weightDto2;
    private WeightDto savedWeightDto1;
    private WeightDto savedWeightDto2;
    private List<WeightDto> weightDtoList;
    private List<WeightDto> savedWeightDtoList;
    private Weight weight1;
    private Weight weight2;
    private Weight savedWeight1;
    private Weight savedWeight2;
    private List<Weight> weightList;
    private List<Weight> savedWeightList;

    private static final String SUV = "SUV";
    private static final String SEDAN = "SEDAN";
    private static final String RANDOM_TYPE = "RANDOM_TYPE";
    private static final String EXCEPTION_OCCURRED = "Duplicate attribute!";

    @BeforeEach
    public void setup() {
        weightDto1 = getWeightDto(SUV);
        weightDto2 = getWeightDto(SEDAN);
        savedWeightDto1 = weightDto1.toBuilder().id(1L).build();
        savedWeightDto2 = weightDto2.toBuilder().id(2L).build();

        weightDtoList = new ArrayList<>();
        weightDtoList.add(weightDto1);
        weightDtoList.add(weightDto2);

        savedWeightDtoList = new ArrayList<>();
        savedWeightDtoList.add(savedWeightDto1);
        savedWeightDtoList.add(savedWeightDto2);

        weight1 = getWeight(SUV);
        weight2 = getWeight(SEDAN);
        savedWeight1 = weight1.toBuilder().id(1L).build();
        savedWeight2 = weight2.toBuilder().id(2L).build();

        weightList = new ArrayList<>();
        weightList.add(weight1);
        weightList.add(weight2);

        savedWeightList = new ArrayList<>();
        savedWeightList.add(savedWeight1);
        savedWeightList.add(savedWeight2);
    }

    @Test
    public void createRankWeights_success() {
        when(modelMapperMock.map(weightDto1, Weight.class)).thenReturn(weight1);
        when(modelMapperMock.map(weightDto2, Weight.class)).thenReturn(weight2);
        when(modelMapperMock.map(savedWeight1, WeightDto.class)).thenReturn(savedWeightDto1);
        when(modelMapperMock.map(savedWeight2, WeightDto.class)).thenReturn(savedWeightDto2);
        when(weightRepositoryMock.findAll()).thenReturn(savedWeightList);

        List<WeightDto> response = weightServiceImpl.createRankWeights(weightDtoList);

        assertNotNull(response);
        assertEquals(savedWeightDtoList, response);

        verify(weightRepositoryMock, times(2)).save(any());
        verify(weightRepositoryMock, times(1)).findAll();
    }

    @Test
    public void createRankWeights_nullResponse() {
        when(modelMapperMock.map(weightDto1, Weight.class)).thenReturn(weight1);
        when(weightRepositoryMock.save(any())).thenThrow(new RuntimeException(EXCEPTION_OCCURRED));

        List<WeightDto> response = weightServiceImpl.createRankWeights(weightDtoList);

        assertNull(response);

        verify(weightRepositoryMock, times(1)).save(any());
    }

    @Test
    public void getRankWeights_success() {
        when(weightRepositoryMock.findAll()).thenReturn(savedWeightList);
        when(modelMapperMock.map(savedWeight1, WeightDto.class)).thenReturn(savedWeightDto1);
        when(modelMapperMock.map(savedWeight2, WeightDto.class)).thenReturn(savedWeightDto2);

        List<WeightDto> response = weightServiceImpl.getRankWeights();

        assertNotNull(response);
        assertEquals(savedWeightDtoList, response);

        verify(weightRepositoryMock, times(1)).findAll();
    }

    @Test
    public void getRankWeightsByType_success() throws ResourceNotFoundException {
        when(weightRepositoryMock.findOneByType(SUV)).thenReturn(savedWeight1);
        when(modelMapperMock.map(savedWeight1, WeightDto.class)).thenReturn(savedWeightDto1);

        WeightDto response = weightServiceImpl.getRankWeightByType(SUV);

        assertNotNull(response);
        assertEquals(savedWeightDto1, response);

        verify(weightRepositoryMock, times(1)).findOneByType(SUV);
    }

    @Test
    public void getRankWeightsByType_nullResponse() throws ResourceNotFoundException {
        when(weightRepositoryMock.findOneByType(RANDOM_TYPE)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            weightServiceImpl.getRankWeightByType(RANDOM_TYPE);
        });

        verify(weightRepositoryMock, times(1)).findOneByType(RANDOM_TYPE);
    }

    private Weight getWeight(String type) {
        return Weight.builder()
                .type(type)
                .year(0.5)
                .seating(0.5)
                .horsepower(0.4)
                .mileage(0.3)
                .price(0.005)
                .build();
    }

    private WeightDto getWeightDto(String type) {
        return WeightDto.builder()
                .type(type)
                .year(0.5)
                .seating(0.5)
                .horsepower(0.4)
                .mileage(0.3)
                .price(0.005)
                .build();
    }
}
