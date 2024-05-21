package com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;

import java.util.Optional;

public interface AuthenticationGateway {

    void authenticate(User c);

    Optional<User> currentUser();
}
