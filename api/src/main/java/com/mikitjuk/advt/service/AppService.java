package com.mikitjuk.advt.service;

import com.mikitjuk.advt.entity.App;
import com.mikitjuk.advt.entity.types.UserRole;
import com.mikitjuk.advt.entity.repository.AppRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AppService {
    @Autowired
    private AppRepository appRepository;
    @Autowired
    private SecurityProviderService securityProviderService;

    public List<App> getApps() {
        return securityProviderService.checkRole(UserRole.PUBLISHER)
                ? securityProviderService.getLoginUser().getApps()
                : appRepository.findAll();
    }

    public App saveApp(App app) {
        securityProviderService.checkAccessApps(app);
        return appRepository.save(app);
    }

    public void deleteApp(Integer appId) {
        securityProviderService.checkAccessApps(appRepository.getOne(appId));
        appRepository.deleteById(appId);
    }
}
