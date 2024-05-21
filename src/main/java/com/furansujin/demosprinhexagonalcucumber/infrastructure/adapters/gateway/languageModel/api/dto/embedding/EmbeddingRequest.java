package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.gateway.languageModel.api.dto.embedding;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class EmbeddingRequest {
    String model;
    @NonNull List<String> input;

    public EmbeddingRequest(String model, @NonNull List<String> input) {
        if (input == null) {
            throw new NullPointerException("input is marked non-null but is null");
        } else {
            this.model = model;
            this.input = input;
        }
    }
}
