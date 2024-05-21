//package com.miraisystems.getsensebackend.infrastructure.config.security.oauth;
//
//import com.miraisystems.getsensebackend.domain.entities.commun.AuthProvider;
//import com.miraisystems.getsensebackend.domain.entities.commun.Role;
//import com.miraisystems.getsensebackend.domain.entities.User;
//import com.miraisystems.getsensebackend.domain.exception.BadRequestException;
//import com.miraisystems.getsensebackend.domain.exception.OAuth2AuthentificationProcessingException;
//import com.miraisystems.getsensebackend.domain.persistence.UserPersistencePort;
//import com.miraisystems.getsensebackend.infrastructure.config.conginext.Oauth2UserInfoFactory;
//import com.miraisystems.getsensebackend.infrastructure.config.conginext.Oauth2userInfo;
//import com.miraisystems.getsensebackend.infrastructure.config.security.AppProperties;
//import com.miraisystems.getsensebackend.infrastructure.config.security.TokenProvider;
//import com.miraisystems.getsensebackend.infrastructure.utils.CookieUtil;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
//import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.io.IOException;
//import java.net.URI;
//import java.util.Collections;
//import java.util.Map;
//import java.util.Optional;
//import java.util.UUID;
//
//import static com.miraisystems.getsensebackend.infrastructure.utils.CotstantUtil.LOCALHOST_MAIN_PAGE;
//import static com.miraisystems.getsensebackend.infrastructure.utils.CotstantUtil.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME;
//import static com.miraisystems.getsensebackend.infrastructure.utils.CotstantUtil.REDIRECT_URI_PARAM_COOKIE_NAME;
//import static com.miraisystems.getsensebackend.infrastructure.utils.CotstantUtil.REGISTRATION_ID;
//import static com.miraisystems.getsensebackend.infrastructure.utils.CotstantUtil.TOKEN;
//
//
//@Component
//public class Oauth2AuthentificationSucessHandler extends SimpleUrlAuthenticationSuccessHandler {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(Oauth2AuthentificationSucessHandler.class);
//
//    private final TokenProvider tokenProvider;
//    private final AppProperties appProperties;
//    private final UserPersistencePort userPersistencePort;
//    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
//
//    public Oauth2AuthentificationSucessHandler(TokenProvider tokenProvider, AppProperties appProperties, UserPersistencePort userPersistencePort, HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
//        this.tokenProvider = tokenProvider;
//        this.appProperties = appProperties;
//        this.userPersistencePort = userPersistencePort;
//        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
//    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication) throws ServletException, IOException {
//        String targetUrl = determineTargetUrl(request, response, authentication);
//
//        if (response.isCommitted()) {
//            LOGGER.debug("Response has already been committed. Unable to redirect to " + targetUrl);
//            return;
//        }
//        clearAuthenticationAttributes(request, response);
//        getRedirectStrategy().sendRedirect(request, response, targetUrl);
//    }
//
//    protected String determineTargetUrl(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication) {
//        User user;
//        Optional<User> userIfExist;
//        DefaultOidcUser defaultOidcUser = (DefaultOidcUser) authentication.getPrincipal();
//        Map<String, Object> attributes = defaultOidcUser.getAttributes();
//
//        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
//                .map(Cookie::getValue);
//
//        OAuth2AuthorizationRequest oAuth2AuthorizationRequest = CookieUtil
//                .getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
//                .map(cookie -> CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class)).orElse(null);
//
//        if (oAuth2AuthorizationRequest == null) {
//            throw new OAuth2AuthentificationProcessingException("OAuth authorization request is null");
//        }
//
//        Map<String, Object> authorizationAttributes = oAuth2AuthorizationRequest.getAttributes();
//        String provider = (String) authorizationAttributes.get(REGISTRATION_ID);
//
//        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
//            throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URI " +
//                    "and can't proceed with the authentication");
//        }
//
//        if (attributes.isEmpty()) {
//            throw new OAuth2AuthentificationProcessingException("Cannot find user data");
//        }
//
//        Oauth2userInfo oAuth2UserInfo = Oauth2UserInfoFactory.getOAuth2UserInfo(provider, attributes);
//        userIfExist = userPersistencePort.findByUsername(oAuth2UserInfo.getEmail());
//
//        user = userIfExist.map(oAuth2User -> updateExistingUser(oAuth2User, oAuth2UserInfo))
//                .orElseGet(() -> registerNewUser(oAuth2UserInfo, provider));
//
//        String targetUrl = redirectUri.orElse(LOCALHOST_MAIN_PAGE);
//        String token = tokenProvider.createToken(user);
//
//        return UriComponentsBuilder.fromUriString(targetUrl)
//                .queryParam(TOKEN, token)
//                .build().toUriString();
//    }
//
//    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
//        super.clearAuthenticationAttributes(request);
//        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
//    }
//
//    private boolean isAuthorizedRedirectUri(String uri) {
//        URI clientRedirectUri = URI.create(uri);
//
//        return appProperties.getOauth2().getAuthorizedRedirectUris()
//                .stream()
//                .anyMatch(authorizedRedirectUri -> {
//                    // Only validate host and port. Let the clients use different paths if they want to
//                    URI authorizedURI = URI.create(authorizedRedirectUri);
//                    if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
//                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
//                        return true;
//                    }
//                    return false;
//                });
//    }
//
//    private User registerNewUser(Oauth2userInfo oAuthUser, String provider) {
//        User user = new User();
//
//        user.setEnabled(true);
//        user.setAuthority(Collections.singleton(Role.ROLE_USER));
//        user.setProvider(AuthProvider.valueOf(provider.toUpperCase()));
//        user.setProviderId(oAuthUser.getId());
//        user.setUsername(oAuthUser.getEmail());
//        user.setFamilyName(oAuthUser.getFamilyName());
//        user.setGivenName(oAuthUser.getGivenName());
//        user.setLocale(oAuthUser.getLocale());
//        user.setGender(oAuthUser.getGender());
//        user.setPassword(UUID.randomUUID().toString());
//        user.setPicture(oAuthUser.getImageUrl());
//        return userPersistencePort.save(user);
//    }
//
//    private User updateExistingUser(User existingUser, Oauth2userInfo oAuth2User) {
//        existingUser.setFamilyName(oAuth2User.getFamilyName());
//        existingUser.setGivenName(oAuth2User.getGivenName());
//        existingUser.setLocale(oAuth2User.getLocale());
//        existingUser.setPicture(oAuth2User.getImageUrl());
//        return userPersistencePort.save(existingUser);
//    }
//}
