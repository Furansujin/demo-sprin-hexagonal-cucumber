package com.furansujin.demosprinhexagonalcucumber.domain.usecases.user;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.exception.NoAuthenticateUserException;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.UseCaseArgumentless;

public class GetProfileUseCase implements UseCaseArgumentless<User > {

    private final AuthenticationGateway authenticationGateway;

    public GetProfileUseCase(AuthenticationGateway authenticationGateway) {
        this.authenticationGateway = authenticationGateway;
    }

    @Override
    public User execute() throws Exception {
        return  this.authenticationGateway.currentUser().orElseThrow(NoAuthenticateUserException::new);
    }
}
