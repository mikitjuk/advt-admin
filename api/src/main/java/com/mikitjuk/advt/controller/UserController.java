package com.mikitjuk.advt.controller;

import com.mikitjuk.advt.auth.AuthJwtToken;
import com.mikitjuk.advt.auth.JwtUtil;
import com.mikitjuk.advt.auth.UserAuthentication;
import com.mikitjuk.advt.convector.UserConverter;
import com.mikitjuk.advt.entity.User;
import com.mikitjuk.advt.model.UserDto;
import com.mikitjuk.advt.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.mikitjuk.advt.auth.SecurityConstants.*;

@Slf4j
@RestController
@RequestMapping(value = Api.ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private JwtUtil jwtUtil;

    @CrossOrigin
    @PostMapping(Api.AUTHENTICATE)
    public ResponseEntity<UserDto> authenticate(@RequestBody User user) {
        log.info("User to login " + user);
        User userLogin = userService.authenticate(user.getEmail());
        String token = jwtUtil.generateToken(userLogin);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_STRING, TOKEN_PREFIX + token);
        headers.setAccessControlExposeHeaders(Collections.singletonList("Authorization"));

        UserAuthentication authentication = new UserAuthentication(
                AuthJwtToken.builder()
                        .userId(userLogin.getId())
                        .role(userLogin.getRole().name())
                        .email(userLogin.getEmail())
                        .build());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<UserDto>(userConverter.convertEntityToDto(userLogin), headers, HttpStatus.OK);
    }

    @GetMapping(Api.Users.USERS)
    public List<UserDto> getUsers() {
        List<User> users = userService.getUsers();
        return userConverter.convertEntityToDto(users);
    }

    @PostMapping(Api.Users.USER)
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = userConverter.convertDtoToEntity(userDto);
        user = userService.createNewUser(user);
        return userConverter.convertEntityToDto(user);
    }

    @PutMapping(Api.Users.USER)
    public UserDto updateUser(@RequestBody UserDto userDto) {
        User user = userConverter.convertDtoToEntity(userDto);
        user = userService.updateUser(user);
        return userConverter.convertEntityToDto(user);
    }

    @DeleteMapping(Api.Users.USERS_BY_ID)
    public void deleteUser(@PathVariable("id") Integer userId) {
        userService.deleteUser(userId);
    }
}
