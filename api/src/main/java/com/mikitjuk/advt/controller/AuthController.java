package com.mikitjuk.advt.controller;

import com.mikitjuk.advt.auth.JwtUtil;
import com.mikitjuk.advt.auth.UserAuthentication;
import com.mikitjuk.advt.convector.UserConverter;
import com.mikitjuk.advt.entity.User;
import com.mikitjuk.advt.model.UserDto;
import com.mikitjuk.advt.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static com.mikitjuk.advt.config.SecurityConstants.HEADER_STRING;
import static com.mikitjuk.advt.config.SecurityConstants.TOKEN_PREFIX;

@Slf4j
@RestController
@RequestMapping(value = Api.ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(Api.AUTHENTICATE)
    public ResponseEntity<UserDto> authenticate(@RequestBody User user) {
        log.info("User to login " + user);

        User userLogin = userService.authenticate(user.getEmail());
        String token = jwtUtil.generateToken(userLogin);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_STRING, TOKEN_PREFIX + token);
        headers.setAccessControlExposeHeaders(Collections.singletonList(HEADER_STRING));

        String authJwtToken = jwtUtil.generateToken(userLogin);
        UserAuthentication authentication = new UserAuthentication(jwtUtil.parseToken(authJwtToken));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>(userConverter.convertEntityToDto(userLogin), headers, HttpStatus.OK);
    }
}
