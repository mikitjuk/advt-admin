package com.mikitjuk.advt.convector;

import com.mikitjuk.advt.entity.App;
import com.mikitjuk.advt.entity.User;
import com.mikitjuk.advt.entity.repository.UserRepository;
import com.mikitjuk.advt.model.AppDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AppConverter {
    @Autowired
    private UserRepository userRepository;

    public App toEntity(AppDto appDto) {
        User user = Objects.isNull(appDto.getUserId())
                ? new User()
                : userRepository.getOne(appDto.getUserId());
        return App.builder()
                .id(appDto.getId())
                .name(appDto.getName())
                .type(appDto.getType())
                .user(user)
                .contentTypes(appDto.getContentTypes())
                .build();
    }

    public AppDto convertEntityToDto(App app) {
        log.info("App before convert " + app);
        AppDto appDto = AppDto.builder()
                .id(app.getId())
                .name(app.getName())
                .type(app.getType())
                .contentTypes(app.getContentTypes())
                .userId(app.getUser().getId())
                .build();
        log.info("App after convert " + appDto);
        return appDto;
    }

    public List<AppDto> convertEntityToDto(List<App> app) {
        return app.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }
}
