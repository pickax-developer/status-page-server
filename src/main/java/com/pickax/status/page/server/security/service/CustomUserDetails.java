package com.pickax.status.page.server.security.service;

import java.util.Collection;

import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pickax.status.page.server.domain.model.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@ToString
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

	private final Long id;

	private final String password;


	public static CustomUserDetails of(User user) {
		return new CustomUserDetails(
				user.getId(),
				user.getPassword()
		);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return id.toString();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
