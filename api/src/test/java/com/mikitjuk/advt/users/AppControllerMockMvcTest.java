package com.mikitjuk.advt.users;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.mikitjuk.advt.AdvtApplicationTests;
import com.mikitjuk.advt.domain.AppType;
import com.mikitjuk.advt.domain.ContentType;
import com.mikitjuk.advt.domain.UserRole;
import com.mikitjuk.advt.model.AppDto;
import com.mikitjuk.advt.model.UserDto;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DatabaseSetup({"classpath:datasets/AddTestUser.xml",
        "classpath:datasets/AddTestApp.xml"})
public class AppControllerMockMvcTest extends AdvtApplicationTests {

    @Test
    @WithMockUser(username = "test user 10", roles = "PUBLISHER")
    public void getApps() throws Exception {
        mockMvc.perform(get("/api/apps")
                .with(postProcessor))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));
    }

    @Test
    @ExpectedDatabase(value = "classpath:datasets/expected/ExpectedCreateApps.xml",
            table = "apps",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @WithMockUser(roles = "PUBLISHER")
    public void shouldAddApp() throws Exception {
        AppDto appDto = createTestAppDto();

        mockMvc.perform(post("/api/app")
                .with(postProcessor)
                .content(objectMapper.writeValueAsString(appDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(appDto.getName())))
                .andExpect(jsonPath("$.type", is(appDto.getType().toString())))
                .andExpect(jsonPath("$.user_id", is(appDto.getUserId())))
                .andExpect(jsonPath("$.content_types.size()", is(appDto.getContentTypes().size())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldNotAddApp_forbidden() throws Exception {
        AppDto appDto = createTestAppDto();

        mockMvc.perform(post("/api/app")
                .with(postProcessor)
                .content(objectMapper.writeValueAsString(appDto)))
                .andExpect(status().isForbidden());
    }

    @Test
    @ExpectedDatabase(value = "classpath:datasets/expected/ExpectedUpdateApps.xml",
            table = "apps",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @WithMockUser(roles = "PUBLISHER")
    public void shouldUpdateApp() throws Exception {
        AppDto appDto = createTestAppDto();
        appDto.setId(10);

        mockMvc.perform(put("/api/app")
                .with(postProcessor)
                .content(objectMapper.writeValueAsString(appDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(appDto.getName())))
                .andExpect(jsonPath("$.type", is(appDto.getType().toString())))
                .andExpect(jsonPath("$.user_id", is(appDto.getUserId())))
                .andExpect(jsonPath("$.content_types.size()", is(appDto.getContentTypes().size())));
    }

    @Test
    @ExpectedDatabase(value = "classpath:datasets/expected/ExpectedDeleteApps.xml",
            table = "apps",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @WithMockUser(roles = "PUBLISHER")
    public void deleteApp() throws Exception {
        mockMvc.perform(delete("/api/apps/10")
                .with(postProcessor))
                .andExpect(status().isOk());
    }

    private AppDto createTestAppDto() {
        return AppDto.builder()
                .name("app test")
                .type(AppType.ANDROID)
                .contentTypes(Sets.newLinkedHashSet(ContentType.HTML, ContentType.VIDEO))
                .userId(10)
                .build();
    }
}
