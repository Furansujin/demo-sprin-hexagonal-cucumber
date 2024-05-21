package com.furansujin.demosprinhexagonalcucumber.infrastructure.config;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

//    @Value("${jwt.secret}")
    private String secret = "bV5UUwGmu2R5IG7mhmH1256THKV4KMi3";

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }



    //generate token for user
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getUsername());
        claims.put("family_name", user.getFamilyName());
        claims.put("given_name", user.getGivenName());
        claims.put("picture", user.getPicture());
        claims.put("locale", user.getLocale());


        return doGenerateToken(claims, user.getUsername());
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }




    public User getUserFromToken(String token) {
        if(token == null) return null;
        // Extraire les claims du token
        Claims claims = getAllClaimsFromToken(token);
        if(claims == null) return null;
        // Créer et renvoyer un nouvel utilisateur à partir des claims
        User user = new User();

        user.setUsername((String) claims.get("email"));

        user.setFamilyName((String) claims.get("family_name"));
        user.setGivenName((String) claims.get("given_name"));
        user.setPicture((String) claims.get("picture"));
        user.setLocale((String) claims.get("locale"));

        // Ajoutez ici d'autres champs au besoin

        return user;
    }



    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return null;
        }
    }


    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }





    public boolean validateToken(String token, UserDetails userDetails) {
        User user = getUserFromToken(token);
        return (user.getUsername().equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

}
