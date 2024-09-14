package com.aryanabhi.recommendation.dto;

import jakarta.annotation.Nullable;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ComparisonRequestDto {
    @Nullable
    Boolean hideSimilarities;
    List<Long> ids;
}
