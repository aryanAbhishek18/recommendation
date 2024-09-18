package com.aryanabhi.recommendation.dto;

import jakarta.annotation.Nullable;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class ComparisonRequestDto {
    @Nullable
    private Boolean hideSimilarities;
    private List<Long> ids;
}
