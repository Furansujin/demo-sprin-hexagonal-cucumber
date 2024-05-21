package com.furansujin.demosprinhexagonalcucumber.domain.exception;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.SourceType;

import java.util.UUID;

public class UserSourceNotFoundException extends RuntimeException {
    public UserSourceNotFoundException(SourceType sourceType, UUID userId) {
        super("No source found with type: " + sourceType + " for user ID: " + userId);
    }
}