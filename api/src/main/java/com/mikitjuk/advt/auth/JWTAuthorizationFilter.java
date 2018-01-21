package com.mikitjuk.advt.auth;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.mikitjuk.advt.auth.SecurityConstants.HEADER_STRING;
import static com.mikitjuk.advt.auth.SecurityConstants.SECRET;
import static com.mikitjuk.advt.auth.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtUtil jwtUtil;

    public JWTAuthorizationFilter(AuthenticationManager authManager, JwtUtil jwtUtil) {
        super(authManager);
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
//            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");

            chain.doFilter(req, res);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(req));
        chain.doFilter(req, res);
    }

    private Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            AuthJwtToken authJwtToken = jwtUtil.parseToken(token);
            if (authJwtToken != null) {
                return new UserAuthentication(authJwtToken);
            }
            return null;
        }
        return null;
    }
}