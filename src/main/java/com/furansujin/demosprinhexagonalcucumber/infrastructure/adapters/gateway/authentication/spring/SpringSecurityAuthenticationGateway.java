package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.gateway.authentication.spring;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;

import com.furansujin.demosprinhexagonalcucumber.domain.persistence.UserPersistencePort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SpringSecurityAuthenticationGateway implements AuthenticationGateway {



//    private final AuthenticationManager authenticationManager;
    private final UserPersistencePort userPersistencePort;

    public SpringSecurityAuthenticationGateway(  UserPersistencePort userPersistencePort) {

        this.userPersistencePort = userPersistencePort;

    }

    @Override
    public void authenticate(User c) {
        try {
            Authentication authRequest = createAuthenticationToken(c);

            // Perform the authentication
//            Authentication authentication = authenticationManager.authenticate(authRequest);
//
//            // Store the authentication in the security context
//            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            // Handle authentication failure (e.g., invalid credentials)
        }
    }

    private Authentication createAuthenticationToken(User c) {
        // Logic to create the appropriate authentication token (e.g., UsernamePasswordAuthenticationToken or OAuth2AuthenticationToken)
        return new UsernamePasswordAuthenticationToken(c.getUsername(), c.getPassword());


    }

    @Override
    public Optional<User> currentUser() {
        // Retrieve the current user from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // Assuming the principal is an instance of User
            return userPersistencePort.findByUsername(authentication.getName());
        }
        return Optional.empty();
    }
}