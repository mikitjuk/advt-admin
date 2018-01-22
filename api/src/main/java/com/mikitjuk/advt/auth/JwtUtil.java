package com.mikitjuk.advt.auth;

import com.mikitjuk.advt.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

import static com.mikitjuk.advt.config.SecurityConstants.EXPIRATION_TIME;
import static com.mikitjuk.advt.config.SecurityConstants.SECRET;
import static com.mikitjuk.advt.config.SecurityConstants.TOKEN_PREFIX;

public class JwtUtil {

    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_ROLE = "role";
    private static final String KEY_USER_EMAIL = "email";

    public AuthJwtToken parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();

            return AuthJwtToken.builder()
                    .email(body.getSubject())
                    .userId(Integer.valueOf((String) body.get(KEY_USER_ID)))
                    .role((String) body.get(KEY_USER_ROLE))
                    .build();

        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

    public String generateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put(KEY_USER_ID, user.getId() + "");
        claims.put(KEY_USER_EMAIL, user.getEmail());
        claims.put(KEY_USER_ROLE, user.getRole().name());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
    }
}