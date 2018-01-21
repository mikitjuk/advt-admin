package com.mikitjuk.advt.auth;

import com.mikitjuk.advt.entity.types.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthJwtToken {
    private Integer userId;
    private String email;
    private String role;
}
