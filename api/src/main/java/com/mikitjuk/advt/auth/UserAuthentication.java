package com.mikitjuk.advt.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class UserAuthentication implements Authentication {

	private boolean authenticated = true;
	private AuthJwtToken authJwtToken;

	public UserAuthentication(AuthJwtToken authJwtToken) {
		this.authJwtToken = authJwtToken;
	}

	@Override
	public String getName() {
		return authJwtToken.getEmail();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority(authJwtToken.getRole()));
	}

	@Override
	public Object getCredentials() {
		return authJwtToken.getUserId();
	}

	@Override
	public AuthJwtToken getDetails() {
		return authJwtToken;
	}

	@Override
	public Object getPrincipal() {
		return authJwtToken.getUserId();
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
}