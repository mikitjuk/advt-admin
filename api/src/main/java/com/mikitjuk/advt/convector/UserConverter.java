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
        User user = User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .build();


//        new UserEntity();
//        user.setUserId(userDto.getUserId());
//        user.setEmail(userDto.getEmail());
//        user.setFirstName(userDto.getFirstName());
//        user.setLastName(userDto.getLastName());
//        user.setMiddleName(userDto.getMiddleName());
//        user.setPosition(userDto.getPosition());
//        if (null != userDto.getLocaleKey()) {
//            user.setLocaleKey(userDto.getLocaleKey());
//        }
//        user.setActivity(userDto.getActivity());
//        user.setUserType(null != userDto.getUserTypeKey() ? UserType.builder().userTypeId(userDto.getUserTypeKey().getId()).build() : null);
//        user.setPassword(userDto.getPlainPassword());
//
//        convertPhones(userDto, user);
//        convertContacts(userDto, user);

        return user;
    }

    public UserDto convertEntityToDto(User user) {
        log.info("User before convert " + user);
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
