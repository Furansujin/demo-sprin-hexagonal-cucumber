package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.authentication.payload;

import com.furansujin.demosprinhexagonalcucumber.domain.usecases.authentication.RegisterUserUseCase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

    private String email;

    private String password;

    private String firstName;

    private String familyName;


    public RegisterUserUseCase.RegisterUserRequest toDomain() {

        return new RegisterUserUseCase.RegisterUserRequest(
                this.email,
                this.password,
                this.firstName,
                this.familyName
        );

    }
}
