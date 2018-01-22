package com.mikitjuk.advt.convector;

import com.mikitjuk.advt.entity.User;
import com.mikitjuk.advt.model.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserConverter {

    public User convertDtoToEntity(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .build();
    }

    public UserDto convertEntityToDto(User user) {
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
        log.info("User after convert " + userDto);
        return userDto;
    }

    public List<UserDto> convertEntityToDto(List<User> users) {
        return users.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }
}
