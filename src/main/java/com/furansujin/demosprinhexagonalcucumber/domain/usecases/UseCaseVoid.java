package com.furansujin.demosprinhexagonalcucumber.domain.usecases;

public interface UseCaseVoid<Argument> {
    void execute(Argument arg) throws Exception;
}