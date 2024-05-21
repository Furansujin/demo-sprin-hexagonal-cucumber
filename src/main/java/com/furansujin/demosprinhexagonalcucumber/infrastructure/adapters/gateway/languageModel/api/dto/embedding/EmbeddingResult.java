package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.gateway.languageModel.api.dto.embedding;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddingResult {
    private String model;
    private String object;
    private List<EmbeddingDto> data;
}
