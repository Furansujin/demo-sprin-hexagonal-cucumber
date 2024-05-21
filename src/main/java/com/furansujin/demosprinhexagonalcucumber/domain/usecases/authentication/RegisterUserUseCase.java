package com.furansujin.demosprinhexagonalcucumber.domain.usecases.authentication;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.AuthProvider;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.Role;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.UserPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.UseCase;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

public class RegisterUserUseCase implements UseCase<User, RegisterUserUseCase.RegisterUserRequest> {


    private final UserPersistencePort userPersistencePort;

    public RegisterUserUseCase(  UserPersistencePort userPersistencePort) {

        this.userPersistencePort = userPersistencePort;
    }
    @Override
    public User execute(RegisterUserUseCase.RegisterUserRequest arg) throws Exception {

        Optional<User> userFromDB = this.userPersistencePort.findByUsername(arg.email);

        if (userFromDB.isPresent()) {
            throw new BadCredentialsException("Username is already exists");
        }

        User user = new User();
        user.setUsername(arg.email);
//        user.setPassword(encoder.encode(arg.password));
        user.setPassword( arg.password);
        user.setGivenName(arg.firstName);
        user.setFamilyName(arg.familyName);
        user.setProvider(AuthProvider.LOCAL);
        user.setEnabled(true);
        user.setAuthority(Collections.singleton(Role.ROLE_USER));

        return this.userPersistencePort.save(user);

    }
    public record RegisterUserRequest(String email, String password, String firstName, String familyName) {
    }
}
