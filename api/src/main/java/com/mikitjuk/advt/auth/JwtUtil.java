package com.mikitjuk.advt.auth;

import com.mikitjuk.advt.entity.User;
import com.mikitjuk.advt.entity.types.UserRole;
import com.mikitjuk.advt.model.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

import static com.mikitjuk.advt.auth.SecurityConstants.EXPIRATION_TIME;
import static com.mikitjuk.advt.auth.SecurityConstants.SECRET;
import static com.mikitjuk.advt.auth.SecurityConstants.TOKEN_PREFIX;

public class JwtUtil {

    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     *
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    public AuthJwtToken parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();

            return AuthJwtToken.builder()
                    .email(body.getSubject())
                    .userId(Integer.valueOf((String) body.get("userId")))
                    .role((String) body.get("role"))
                    .build();

        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * User object. Tokens validity is infinite.
     *
     * @param user the user for which the token will be generated
     * @return the JWT token
     */
    public String generateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("userId", user.getId() + "");
        claims.put("role", user.getRole().name());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
    }
}