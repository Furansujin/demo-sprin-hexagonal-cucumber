package com.furansujin.demosprinhexagonalcucumber.domain.usecases.authentication;

import com.furansujin.demosprinhexagonalcucumber.domain.usecases.UseCase;
 import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class AuthenticateUserUseCase implements UseCase<String, AuthenticateUserUseCase.AuthenticateUserRequest > {

//    private final TokenProvider tokenProvider;

    public AuthenticateUserUseCase(  ) {

    }
    @Override
    public String execute(AuthenticateUserUseCase.AuthenticateUserRequest arg) throws Exception {

//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        arg.email,
//                        arg.password
//                )
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return tokenProvider.createToken(authentication);
        return null;
    }

    public record AuthenticateUserRequest(String email, String password) {
    }
}
