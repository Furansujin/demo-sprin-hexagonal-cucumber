package com.furansujin.demosprinhexagonalcucumber.acceptance.common;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.UserPersistencePort;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthenticationStepdefs {

    private final AuthenticationGateway authenticationGateway;
    private final UserPersistencePort userPersistencePort;
    public AuthenticationStepdefs(AuthenticationGateway authenticationGateway, UserPersistencePort userPersistencePort) {
        this.authenticationGateway = authenticationGateway;
        this.userPersistencePort = userPersistencePort;
    }

    @Given("user exist with id {string}")
    public void userExistWithId(String userId) {
        User user = new User();
        user.setId(UUID.fromString(userId));
        user.setFamilyName("Doe");
        user.setGivenName("John");
        user.setUsername("doe@gmail.com");
        this.userPersistencePort.save(user);
        Optional<User> byId = this.userPersistencePort.findById(UUID.fromString(userId));
        assertTrue(byId.isPresent());
    }

    @Given("user with ID {string} is logged in")
    public void userWithIDIsLoggedIn(String userId) {
        User user = new User();
        user.setId(UUID.fromString(userId));
        user.setFamilyName("Doe");
        user.setGivenName("John");
        user.setUsername("doe@gmail.com");
        authenticationGateway.authenticate(user);
        assertTrue(authenticationGateway.currentUser().isPresent());
        this.userPersistencePort.save(user);
        Optional<User> byId = this.userPersistencePort.findById(UUID.fromString(userId));
        assertTrue(byId.isPresent());

    }


    @And("user with email {string} exists")
    public void userWithEmailExists(String emailUser) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(emailUser);
        user.setFamilyName("familyName");
        user.setGivenName("givenName");
        User save = this.userPersistencePort.save(user);
        assertNotNull(save);

    }

}
