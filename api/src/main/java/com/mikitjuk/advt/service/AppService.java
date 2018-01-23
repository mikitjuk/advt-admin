package com.mikitjuk.advt.service;

import com.mikitjuk.advt.entity.App;
import com.mikitjuk.advt.entity.types.UserRole;
import com.mikitjuk.advt.entity.repository.AppRepository;
import com.mikitjuk.advt.exception.EntityNotFoundException;
import com.mikitjuk.advt.exception.ForbiddenException;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public App getApp(Integer appId) {
        return appRepository.findById(appId)
                .map(securityProviderService::checkAccessApps)
                .orElseThrow(() -> new EntityNotFoundException("App not found"));
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
