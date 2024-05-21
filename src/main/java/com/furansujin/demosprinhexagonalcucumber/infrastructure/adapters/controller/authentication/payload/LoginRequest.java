package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.authentication.payload;

import com.furansujin.demosprinhexagonalcucumber.domain.usecases.authentication.AuthenticateUserUseCase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String email;
    private String password;

    public AuthenticateUserUseCase.AuthenticateUserRequest toDomain() {
        return new AuthenticateUserUseCase.AuthenticateUserRequest(email, password);
    }
}
