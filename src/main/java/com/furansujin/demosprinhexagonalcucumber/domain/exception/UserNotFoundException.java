package com.furansujin.demosprinhexagonalcucumber.domain.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID userId) {
        super("No user found with ID: " + userId);
    }
}