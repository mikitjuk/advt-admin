package com.mikitjuk.advt.service;

import com.mikitjuk.advt.entity.User;
import com.mikitjuk.advt.entity.types.UserRole;
import com.mikitjuk.advt.entity.repository.UserRepository;
import com.mikitjuk.advt.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public User saveUser(User user) {
        securityProviderService.checkAccessUsers(user);
        return userRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        securityProviderService.checkAccessUsers(userRepository.getOne(userId));
        userRepository.deleteById(userId);
    }

    public User authenticate(String email) throws AuthenticationCredentialsNotFoundException {
        return Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public User getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
