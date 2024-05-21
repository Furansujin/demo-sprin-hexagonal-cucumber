package com.furansujin.demosprinhexagonalcucumber.domain.exception;

import java.util.UUID;

public class SourceNotFoundException extends RuntimeException {
    public SourceNotFoundException(UUID sourceId) {
        super("No source found with ID: " + sourceId);
    }
}