package com.furansujin.demosprinhexagonalcucumber.domain.exception;

import java.util.UUID;

public class NoAuthenticateUserException extends RuntimeException {
    public NoAuthenticateUserException() {
        super("No authenticate user");
    }
}