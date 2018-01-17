package com.mikitjuk.advt.service;

import com.mikitjuk.advt.domain.App;
import com.mikitjuk.advt.domain.User;
import com.mikitjuk.advt.domain.UserRole;
import com.mikitjuk.advt.domain.repository.UserRepository;
import com.mikitjuk.advt.exception.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SecurityProviderService {

	@Autowired
	private UserRepository userRepository;


	public boolean checkRole(UserRole userRole) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + userRole.toString()));
	}

	public User getLoginUser() {
		return userRepository.findByEmail(getEmailLoginUser());
	}

	public String getEmailLoginUser() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	public void checkAccessByRole(User user, UserRole userRoleChecked) {
		if (!checkRole(UserRole.ADMIN) && Objects.nonNull(user) && !user.getRole().equals(userRoleChecked)) {
			throw new ForbiddenException("Not allow role user");
		}
	}

	public void checkAccessApp(App app) {
		if (checkRole(UserRole.PUBLISHER) && !app.getUser().getEmail().equals(getEmailLoginUser())) {
			throw new ForbiddenException("User registration only our app");
		}
	}
}
