package com.mikitjuk.advt.model;

import com.mikitjuk.advt.entity.types.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Integer id;
    private String name;
    private String email;
    private UserRole role;
    private String token;
}
