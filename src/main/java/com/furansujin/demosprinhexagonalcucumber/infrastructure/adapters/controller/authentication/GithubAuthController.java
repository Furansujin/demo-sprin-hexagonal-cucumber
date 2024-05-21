package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.authentication;

import com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.github.AuthGithubUseCase;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequiredArgsConstructor
public class GithubAuthController {

    @Value("${github.clientId}")
    private String clientId;

    @Value("${github.redirectUri}")
    private String redirectUri;

    @Value("${redirect.baseurl}")
    private String redirectBaseUrl;

    private final AuthGithubUseCase authGithubUseCase;

    @GetMapping("/auth/github")
    public RedirectView githubLogin() {
        String uriString = UriComponentsBuilder.fromUriString("https://github.com/login/oauth/authorize")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", "user:email")
                .build()
                .toUriString();
        return new RedirectView(uriString);
    }


    @GetMapping("/auth/github/callback")
    public RedirectView githubCallback(@RequestParam("code") String code, HttpServletRequest request) {
        authGithubUseCase.execute(new AuthGithubUseCase.AuthGithubRequest(code));

        String uriString = UriComponentsBuilder.fromUriString(redirectBaseUrl + "/redirect" )
                .queryParam("state", true)
                .build()
                .toUriString();
        return new RedirectView(uriString);
    }
}