package com.mikitjuk.advt.service;

import com.mikitjuk.advt.domain.App;
import com.mikitjuk.advt.domain.UserRole;
import com.mikitjuk.advt.domain.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public App createNewApp(App app) {
        securityProviderService.checkAccessApp(app);
        return appRepository.save(app);
    }

    public App updateApp(App app) {
        securityProviderService.checkAccessApp(app);
        return appRepository.save(app);
    }

    public void deleteApp(Integer appId) {
        securityProviderService.checkAccessApp(appRepository.getOne(appId));
        appRepository.deleteById(appId);
    }
}
