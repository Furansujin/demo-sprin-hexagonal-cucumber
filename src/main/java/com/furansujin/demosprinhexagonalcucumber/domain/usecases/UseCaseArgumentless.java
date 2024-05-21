package com.furansujin.demosprinhexagonalcucumber.domain.usecases;

public interface UseCaseArgumentless<Result> {
    Result execute() throws Exception;
}