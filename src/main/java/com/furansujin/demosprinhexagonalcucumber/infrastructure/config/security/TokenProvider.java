//package com.miraisystems.getsensebackend.infrastructure.config.security;
//
//
//import com.miraisystems.getsensebackend.domain.entities.User;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.UnsupportedJwtException;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//import java.util.Date;
//import java.util.UUID;
//
//import static com.miraisystems.getsensebackend.infrastructure.utils.CotstantUtil.AUTHORIZATION_SECURITY_HEADER;
//import static com.miraisystems.getsensebackend.infrastructure.utils.CotstantUtil.BEARER;
//
//
//@Service
//@RequiredArgsConstructor
//public class TokenProvider {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(TokenProvider.class);
//
//    private final AppProperties appProperties;
//    private final CustomUserDetailsService customUserDetailsService;
//
//    public String createToken(Authentication authentication) {
//        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());
//
//        return Jwts.builder()
//                .setSubject( userPrincipal.getId().toString())
//                .setIssuedAt(new Date())
//                .setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
//                .compact();
//    }
//
//    public String createToken(User user) {
//
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());
//
//        return Jwts.builder()
//                .setSubject(user.getId().toString())
//                .setIssuedAt(new Date())
//                .setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
//                .compact();
//    }
//
//    public UUID getUserIdFromToken(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(appProperties.getAuth().getTokenSecret())
//                .parseClaimsJws(token)
//                .getBody();
//
//        return UUID.fromString(claims.getSubject());
//    }
//
//    public UsernamePasswordAuthenticationToken getAuthenticationByUserFromDbWithId(String token) {
//        UserDetails userDetails = customUserDetailsService.loadUserById(getUserIdFromToken(token));
//        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//    }
//
//    public String getJwtFromRequest(HttpServletRequest request) {
//        int bearerTokenStartsFrom = 7;
//
//        String bearerToken = request.getHeader(AUTHORIZATION_SECURITY_HEADER);
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
//            return bearerToken.substring(bearerTokenStartsFrom, bearerToken.length());
//        }
//        return null;
//    }
//
//    public boolean validateToken(String authToken) {
//        try {
//            Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
//            return true;
//        } catch (MalformedJwtException ex) {
//            LOGGER.error("Invalid JWT token");
//        } catch (ExpiredJwtException ex) {
//            LOGGER.error("Expired JWT token");
//        } catch (UnsupportedJwtException ex) {
//            LOGGER.error("Unsupported JWT token");
//        } catch (IllegalArgumentException ex) {
//            LOGGER.error("JWT claims string is empty.");
//        }
//        return false;
//    }
//}
