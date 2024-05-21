package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.gateway.authentication.InMemory;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;

import java.util.Optional;

public class InMemoryAuthenticationGateway implements AuthenticationGateway {

    private User currentUser;

    @Override
    public void authenticate(User c) {
        this.currentUser = c;
    }

    @Override
    public Optional<User> currentUser() {
        return Optional.ofNullable(currentUser);
    }
}
