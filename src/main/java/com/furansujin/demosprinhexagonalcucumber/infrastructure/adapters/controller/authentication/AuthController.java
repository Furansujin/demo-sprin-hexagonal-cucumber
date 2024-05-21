package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.authentication;


import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.sources.GithubGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.authentication.AuthenticateUserUseCase;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.authentication.RegisterUserUseCase;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.github.AuthGithubUseCase;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.authentication.payload.LoginRequest;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.authentication.payload.SignUpRequest;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.authentication.payload.TokenResponse;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.payload.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final AuthGithubUseCase authGithubUseCase;
    private final GithubGateway githubGateway;



    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) throws Exception {

        String token = this.authenticateUserUseCase.execute(loginRequest.toDomain());

        return ResponseEntity.ok(new TokenResponse(token));
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) throws Exception {
        User userAfterSaving = this.registerUserUseCase.execute(signUpRequest.toDomain());


        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(userAfterSaving.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully@"));
    }

//
//    @GetMapping("/github/callback")
//    public ResponseEntity<Map<String, Object>> githubCallback(@RequestParam String code) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = (User) authentication.getPrincipal();
//        Map<String, Object> response = new HashMap<>();
//        HttpStatus status;
//        try {
//            this.authGithubUseCase.execute(new AuthGithubUseCase.AuthGithubRequest(code));
//
//            status = HttpStatus.OK;
//        } catch (Exception e) {
//            e.printStackTrace();
//
//            response.put("status", "error");
//            response.put("message", "An error occurred while processing the callback");
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//
//        return new ResponseEntity<>(response, status);
//    }
//
//
//    @GetMapping("/github")
//    public RedirectView githubLogin() {
//        String uri = this.githubGateway.generateGithubAuthorizeUrl();
//
//        return new RedirectView(uri);
//    }
//
//    @GetMapping("/google")
//    public RedirectView googleLogin() {
//        String uri = UriComponentsBuilder.fromHttpUrl("https://accounts.google.com/o/oauth2/v2/auth")
//                .queryParam("client_id", "685143589671-bo2in9io8m5tue3lo83o0joodm1avs3u.apps.googleusercontent.com")
//                .queryParam("redirect_uri", "http://localhost:8080/auth/google/callback")
//                .queryParam("response_type", "code")
//                .queryParam("scope", "openid email profile")
//                .toUriString();
//        return new RedirectView(uri);
//    }


//    @GetMapping("/google/callback")
//    public void googleCallback(@RequestParam String code, HttpServletResponse response) {
//        // Vérifier si le code est nul ou vide
//        if (code == null || code.isEmpty()) {
//            redirectWithError(response, "Invalid code");
//            return;
//        }
//
//        try {
//            String accessToken = googleApiService.exchangeCodeForAccessToken(code);
//
//            Map<String, Object> userInfo = googleApiService.getUserInfo(accessToken);
//
//            User user = new User();
//            user.setEmail((String) userInfo.get("email"));
//            user.setVerifiedEmail((boolean) userInfo.get("verified_email"));
//            user.setFamilyName((String) userInfo.get("family_name"));
//            user.setGivenName((String) userInfo.get("given_name"));
//            user.setPicture((String) userInfo.get("picture"));
//            user.setLocale((String) userInfo.get("locale"));
//
//            // Initialisez ici les autres champs de l'utilisateur si nécessaire
//            User userSaved = userService.loadUserByEmailOrCreate(user);
//
//            String jwt = jwtTokenUtil.generateToken(userSaved);
//
//            Cookie jwtCookie = new Cookie("jwt", jwt);
//            jwtCookie.setHttpOnly(true);
//            jwtCookie.setMaxAge(7 * 24 * 60 * 60); // Expire after one week
//            jwtCookie.setPath("/");
//            // TODO: Uncomment for production
//            jwtCookie.setSecure(false); // Only send cookie over HTTPS
//
//            response.addCookie(jwtCookie);
//            response.sendRedirect("http://localhost:4200");
//        } catch (Exception e) {
//            e.printStackTrace();
//            redirectWithError(response, "An error occurred while processing the callback");
//        }
//    }
//
//    private void redirectWithError(HttpServletResponse response, String errorMessage) {
//        try {
//            response.sendRedirect("http://localhost:4200/error?message=" + URLEncoder.encode(errorMessage, "UTF-8"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
//    @GetMapping("/validate")
//    public
//    ResponseEntity<User> validateToken(HttpServletResponse response, HttpServletRequest request) {
//        Map<String, Object> responseBody = new HashMap<>();
//        HttpStatus status;
//
//        // Récupérer le cookie
//        Cookie[] cookies = request.getCookies();
//        System.out.println(cookies);
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("jwt")) {
//                    String token = cookie.getValue();
//                    System.out.println(token);
//                    User user = jwtTokenUtil.getUserFromToken(token);
//                    if (jwtTokenUtil.validateToken(token, user)) {
//                        responseBody.put("status", "success");
//                        responseBody.put("message", "JWT valid");
//                        responseBody.put("userInfo", user); // Ajouter les informations de l'utilisateur à la réponse
//                        status = HttpStatus.OK;
//                        return new ResponseEntity<>(user, status);
//                    }
//                }
//            }
//        }
//
//        responseBody.put("status", "error");
//        responseBody.put("message", "JWT invalid or not found");
//        status = HttpStatus.UNAUTHORIZED;
//        return new ResponseEntity<>(null, status);
//    }

}
