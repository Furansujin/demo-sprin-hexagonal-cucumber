package com.furansujin.demosprinhexagonalcucumber.domain.persistence;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.SplitRessource;

import java.util.List;
import java.util.Optional;

public interface SplitRessourcePersistencePort {
    Optional<List<SplitRessource>> getSimilarSplitRessource(float[] vector, float minSimilarity, int limit);

    void saveAll(List<SplitRessource> splitRessources);
}