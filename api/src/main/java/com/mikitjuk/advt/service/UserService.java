package com.mikitjuk.advt.service;

import com.mikitjuk.advt.domain.User;
import com.mikitjuk.advt.domain.UserRole;
import com.mikitjuk.advt.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityProviderService securityProviderService;

    public List<User> getUsers() {
        return securityProviderService.checkRole(UserRole.ADOPS)
                ? userRepository.findByRole(UserRole.PUBLISHER)
                : userRepository.findAll();
    }

    public User createNewUser(User user) {
        securityProviderService.checkAccessByRole(user, UserRole.PUBLISHER);
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        securityProviderService.checkAccessByRole(user, UserRole.PUBLISHER);
        return userRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        securityProviderService.checkAccessByRole(userRepository.getOne(userId), UserRole.PUBLISHER);
        userRepository.deleteById(userId);
    }
}
