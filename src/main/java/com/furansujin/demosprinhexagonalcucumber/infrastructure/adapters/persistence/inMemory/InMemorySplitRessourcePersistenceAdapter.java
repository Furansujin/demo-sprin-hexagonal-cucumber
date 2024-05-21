package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.inMemory;


import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SplitRessourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.SplitRessource;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class InMemorySplitRessourcePersistenceAdapter implements SplitRessourcePersistencePort {

    private Set<SplitRessource> splitRessources = new HashSet<>();


    @Override
    public Optional<List<SplitRessource>> getSimilarSplitRessource(float[] vector, float minSimilarity, int limit) {
        List<SplitRessource> list = this.splitRessources.stream().filter(s -> this.getSimilarity(s.getVector(), vector) >= minSimilarity).toList();
        if(!list.isEmpty())
            return Optional.of(list);
        else
            return Optional.of(this.splitRessources.stream().limit(limit).toList());
    }

    @Override
    public void saveAll(List<SplitRessource> splitRessources) {
        this.splitRessources.addAll(splitRessources);

    }

    private float getSimilarity(float[]  vector2, float[] vector1) {
//        float[] vector2 = stringToFloatArray(vector2String);
        float dotProduct = 0.0f;
        float normA = 0.0f;
        float normB = 0.0f;

        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
            normA += (float) Math.pow(vector1[i], 2);
            normB += (float) Math.pow(vector2[i], 2);
        }

        if (normA == 0.0f || normB == 0.0f) {
            return 0.0f; // avoid division by zero
        }

        return (float) (dotProduct / (Math.sqrt(normA) * Math.sqrt(normB)));
    }

    private float[] stringToFloatArray(String vector) {
        // Split the string by commas
        String[] parts = vector.split(",");

        // Allocate an array of floats of the same length as the number of parts
        float[] result = new float[parts.length];

        // Convert each part to a float and store in the result
        for (int i = 0; i < parts.length; i++) {
            result[i] = Float.parseFloat(parts[i].trim());
        }

        return result;
    }

    public void setSplitRessources(Set<SplitRessource> splitRessources) {
        this.splitRessources = splitRessources;
    }

    public Set<SplitRessource> findAll() {
        return this.splitRessources;
    }
}