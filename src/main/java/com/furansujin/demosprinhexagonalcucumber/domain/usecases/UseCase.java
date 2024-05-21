package com.furansujin.demosprinhexagonalcucumber.domain.usecases;

public interface UseCase<Result, Argument> {
    Result execute(Argument arg) throws Exception;
}