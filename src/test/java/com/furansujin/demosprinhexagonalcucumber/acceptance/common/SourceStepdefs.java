package com.furansujin.demosprinhexagonalcucumber.acceptance.common;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Source;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.SourceType;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourcePersistencePort;
import io.cucumber.java.en.And;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SourceStepdefs {

private final SourcePersistencePort sourcePersistencePort;

    public SourceStepdefs(SourcePersistencePort sourcePersistencePort) {
        this.sourcePersistencePort = sourcePersistencePort;
    }



    @And("source exist with id {string} and source type {string} for userId {string}")
    public void sourceExistWithIdAndSourceTypeForUserId(String sourceId, String type, String userId) {

        SourceType sourceType = SourceType.valueOf(type);
        Source source = Source.builder()
                .id(UUID.fromString(sourceId))
                .type(sourceType)
                .userId(UUID.fromString(userId))
                .accessToken("123456789")
                .build();
        this.sourcePersistencePort.save(source);
        Optional<Source> byId = this.sourcePersistencePort.findByIdAndUserId(UUID.fromString(sourceId), UUID.fromString(userId));
        assertTrue(byId.isPresent());
    }

}
