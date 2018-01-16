package com.mikitjuk.advt.model;

import com.mikitjuk.advt.domain.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Integer id;
    private String name;
    private String email;
    private UserRole role;
}
