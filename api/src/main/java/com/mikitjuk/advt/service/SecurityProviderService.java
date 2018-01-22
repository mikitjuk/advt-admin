package com.mikitjuk.advt.service;

import com.mikitjuk.advt.entity.App;
import com.mikitjuk.advt.entity.User;
import com.mikitjuk.advt.entity.types.UserRole;
import com.mikitjuk.advt.entity.repository.UserRepository;
import com.mikitjuk.advt.exception.ForbiddenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class SecurityProviderService {

	@Autowired
	private UserRepository userRepository;


	public boolean checkRole(UserRole userRole) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info(" Authentication = " + authentication.getAuthorities());
		return authentication.getAuthorities().contains(new SimpleGrantedAuthority(userRole.toString()));
	}

	public User getLoginUser() {
		return userRepository.findByEmail(getEmailLoginUser());
	}

	private String getEmailLoginUser() {
		log.info(" Authentication getEmailLoginUser - " + SecurityContextHolder.getContext().getAuthentication());
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	public void checkAccessUsers(User user) {
		if (!checkRole(UserRole.ADMIN) && Objects.nonNull(user) && !user.getRole().equals(UserRole.PUBLISHER)) {
			throw new ForbiddenException("Not allow role user");
		}
	}

	public void checkAccessApps(App app) {
		if (checkRole(UserRole.PUBLISHER) && !app.getUser().getEmail().equals(getEmailLoginUser())) {
			throw new ForbiddenException("User registration only our app");
		}
	}
}
