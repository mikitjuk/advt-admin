package com.mikitjuk.advt.auth;

import com.mikitjuk.advt.entity.User;
import com.mikitjuk.advt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;
//
//    private static List<User> users = new ArrayList();
//
//    public CustomAuthenticationProvider() {
//        users.add(new User("erin", "123", "ROLE_ADMIN"));
//        users.add(new User("mike", "234", "ROLE_ADMIN"));
//    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
//        String name = authentication.getName();
//        Object credentials = authentication.getCredentials();
//        System.out.println("credentials class: " + credentials.getClass());
//        if (!(credentials instanceof String)) {
//            return null;
//        }
//        String password = credentials.toString();
//
//        Optional<User> userOptional = users.stream()
//                .filter(u -> u.match(name, password))
//                .findFirst();
//
//        if (!userOptional.isPresent()) {
//            throw new BadCredentialsException("Authentication failed for " + name);
//        }

        User user = userService.authenticate(authentication.getName());

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        Authentication auth = new
                UsernamePasswordAuthenticationToken(authentication.getName(), null, grantedAuthorities);
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
