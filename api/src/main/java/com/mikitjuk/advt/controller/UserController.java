package com.mikitjuk.advt.controller;

import com.mikitjuk.advt.convector.UserConverter;
import com.mikitjuk.advt.entity.User;
import com.mikitjuk.advt.model.UserDto;
import com.mikitjuk.advt.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = Api.ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter userConverter;


    @GetMapping(Api.Users.USERS)
    public List<UserDto> getUsers() {
        List<User> users = userService.getUsers();
        return userConverter.convertEntityToDto(users);
    }

    @GetMapping(Api.Users.USERS_BY_ID)
    public UserDto getUser(@PathVariable("id") Integer userId) {
        User users = userService.getUserById(userId);
        return userConverter.convertEntityToDto(users);
    }

    @PostMapping(Api.Users.USER)
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = userConverter.convertDtoToEntity(userDto);
        user = userService.saveUser(user);
        return userConverter.convertEntityToDto(user);
    }

    @PutMapping(Api.Users.USERS_BY_ID)
    public UserDto updateUser(@PathVariable("id") Integer userId, @RequestBody UserDto userDto) {
        User user = userConverter.convertDtoToEntity(userDto);
        user = userService.saveUser(user);
        return userConverter.convertEntityToDto(user);
    }

    @DeleteMapping(Api.Users.USERS_BY_ID)
    public void deleteUser(@PathVariable("id") Integer userId) {
        userService.deleteUser(userId);
    }
}
