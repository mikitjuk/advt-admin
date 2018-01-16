package com.mikitjuk.advt.service;

import com.mikitjuk.advt.domain.App;
import com.mikitjuk.advt.domain.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;

@Service
public class AppService {
    @Autowired
    private AppRepository appRepository;

    public List<App> getApps() {
        return appRepository.findAll();
    }

    public App createNewApp(App app) {
        return appRepository.save(app);
    }

    public App updateApp(App app) {
        return appRepository.save(app);
    }

    public void deleteApp(Integer appId) {
        appRepository.deleteById(appId);
    }


}
