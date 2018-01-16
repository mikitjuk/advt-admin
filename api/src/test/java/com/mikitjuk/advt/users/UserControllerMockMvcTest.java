package com.mikitjuk.advt.users;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.mikitjuk.advt.AdvtApplicationTests;
import com.mikitjuk.advt.domain.UserRole;
import com.mikitjuk.advt.model.UserDto;
import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DatabaseSetup("classpath:datasets/AddTestUser.xml")
public class UserControllerMockMvcTest extends AdvtApplicationTests {

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetUsers() throws Exception {
        mockMvc.perform(get("/api/users")
                .with(postProcessor))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(3)));
    }

    @Test
    @ExpectedDatabase(value = "classpath:datasets/expected/ExpectedCreateUsers.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @WithMockUser(roles = "ADMIN")
    public void shouldCreateUser() throws Exception {
        UserDto testUserDto = createTestUserDto();

        mockMvc.perform(post("/api/user")
                .with(postProcessor)
                .content(objectMapper.writeValueAsString(testUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(testUserDto.getName())))
                .andExpect(jsonPath("$.email", is(testUserDto.getEmail())))
                .andExpect(jsonPath("$.role", is(testUserDto.getRole().toString())));
    }

    @Test
    @ExpectedDatabase(value = "classpath:datasets/expected/ExpectedUpdateUsers.xml",
            table = "users",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @WithMockUser(roles = "ADMIN")
    public void shouldUpdateUser() throws Exception {
        UserDto testUserDto = createTestUserDto();
        testUserDto.setId(10);

        mockMvc.perform(put("/api/user")
                .with(postProcessor)
                .content(objectMapper.writeValueAsString(testUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(testUserDto.getName())))
                .andExpect(jsonPath("$.email", is(testUserDto.getEmail())))
                .andExpect(jsonPath("$.role", is(testUserDto.getRole().toString())));
    }

    @Test
    @ExpectedDatabase(value = "classpath:datasets/expected/ExpectedDeleteUsers.xml",
            table = "users",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @WithMockUser(roles = "ADMIN")
    public void shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/10")
                .with(postProcessor))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "PUBLISHER")
    public void shouldNotCreateUserIsForbidden() throws Exception {
        UserDto testUserDto = createTestUserDto();

        mockMvc.perform(post("/api/user")
                .with(postProcessor)
                .content(objectMapper.writeValueAsString(testUserDto)))
                .andExpect(status().isForbidden());
    }

    private UserDto createTestUserDto() {
        return UserDto.builder()
                .email("test1@test.ua")
                .name("test user")
                .role(UserRole.ADMIN)
                .build();
    }
}
