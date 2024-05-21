package com.furansujin.demosprinhexagonalcucumber.domain.exception;

import java.util.UUID;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(UUID projectId) {
        super("No project found with ID: " + projectId);
    }
}