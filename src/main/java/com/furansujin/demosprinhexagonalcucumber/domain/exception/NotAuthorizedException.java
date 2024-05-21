package com.furansujin.demosprinhexagonalcucumber.domain.exception;

import java.util.UUID;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException(UUID id) {
        super("Not authorized to access element with ID: " + id);
    }
}
