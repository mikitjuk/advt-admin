package com.mikitjuk.advt.service;

import com.mikitjuk.advt.entity.User;
import com.mikitjuk.advt.entity.types.UserRole;
import com.mikitjuk.advt.entity.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        securityProviderService.checkAccessUsers(user, UserRole.PUBLISHER);
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        securityProviderService.checkAccessUsers(user, UserRole.PUBLISHER);
        return userRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        securityProviderService.checkAccessUsers(userRepository.getOne(userId), UserRole.PUBLISHER);
        userRepository.deleteById(userId);
    }

    public User authenticate(String email) throws AuthenticationCredentialsNotFoundException {
        return userRepository.findByEmail(email);
    }
}
