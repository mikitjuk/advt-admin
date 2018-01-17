package com.mikitjuk.advt.controller;

import com.mikitjuk.advt.convector.UserConverter;
import com.mikitjuk.advt.domain.User;
import com.mikitjuk.advt.model.UserDto;
import com.mikitjuk.advt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Api.ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter userConverter;

//    @PostMapping("/login")
//    public signUp(@RequestBody User user) {
//        user.login(user.getEmail());
//    }

    @GetMapping(Api.Users.USERS)
//    @PreAuthorize("hasAnyRole('ADMIN', 'ADOPS')")
    public List<UserDto> getUsers() {
        List<User> users = userService.getUsers();
        return userConverter.convertEntityToDto(users);
    }

    @PostMapping(Api.Users.USER)
//    @PreAuthorize("hasAnyRole('ADMIN', 'ADOPS')")
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = userConverter.convertDtoToEntity(userDto);
        user = userService.createNewUser(user);
        return userConverter.convertEntityToDto(user);
    }

    @PutMapping(Api.Users.USER)
//    @PreAuthorize("hasAnyRole('ADMIN', 'ADOPS')")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        User user = userConverter.convertDtoToEntity(userDto);
        user = userService.updateUser(user);
        return userConverter.convertEntityToDto(user);
    }

    @DeleteMapping(Api.Users.USERS_BY_ID)
//    @PreAuthorize("hasAnyRole('ADMIN', 'ADOPS')")
    public void deleteUser(@PathVariable("id") Integer userId) {
        userService.deleteUser(userId);
    }
}
