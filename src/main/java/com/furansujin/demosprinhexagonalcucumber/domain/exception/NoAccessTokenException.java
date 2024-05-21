package com.furansujin.demosprinhexagonalcucumber.domain.exception;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.SourceType;

public class NoAccessTokenException extends RuntimeException {
    public NoAccessTokenException(SourceType sourceTypeEnum) {
        super("No Access Token for this Source: " + sourceTypeEnum.name());
    }
}