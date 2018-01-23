package com.mikitjuk.advt.controller;

import com.mikitjuk.advt.convector.AppConverter;
import com.mikitjuk.advt.entity.App;
import com.mikitjuk.advt.model.AppDto;
import com.mikitjuk.advt.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Api.ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class AppController {
    @Autowired
    private AppService appService;
    @Autowired
    private AppConverter appConverter;

    @GetMapping(Api.Apps.APPS)
    public List<AppDto> getApps() {
        List<App> app = appService.getApps();
        return appConverter.convertEntityToDto(app);
    }

    @GetMapping(Api.Apps.APPS_BY_ID)
    public AppDto getApps(@PathVariable("id") Integer appId) {
        App app = appService.getApp(appId);
        return appConverter.convertEntityToDto(app);
    }

    @PostMapping(Api.Apps.APP)
    public AppDto createApp(@RequestBody AppDto appDto) {
        App app = appConverter.toEntity(appDto);
        app = appService.saveApp(app);
        return appConverter.convertEntityToDto(app);
    }

    @PutMapping(Api.Apps.APPS_BY_ID)
    public AppDto updateApp(@PathVariable("id") Integer appId, @RequestBody AppDto appDto) {
        App app = appConverter.toEntity(appDto);
        app.setId(appId);
        app = appService.saveApp(app);
        return appConverter.convertEntityToDto(app);
    }

    @DeleteMapping(Api.Apps.APPS_BY_ID)
    public void deleteApp(@PathVariable("id") Integer appId) {
        appService.deleteApp(appId);
    }
}
