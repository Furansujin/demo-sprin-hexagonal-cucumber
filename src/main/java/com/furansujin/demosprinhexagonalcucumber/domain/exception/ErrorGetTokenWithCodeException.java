package com.furansujin.demosprinhexagonalcucumber.domain.exception;

public class ErrorGetTokenWithCodeException extends RuntimeException {
    public ErrorGetTokenWithCodeException() {
        super("Error GitHub credentials");
    }
}