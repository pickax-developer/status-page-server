package com.pickax.status.page.server.repository;

import com.pickax.status.page.server.domain.enumclass.UserStatus;
import com.pickax.status.page.server.domain.model.User;

import java.util.Optional;

public interface UserRepositoryCustom {
	Optional<User> getUser(String email, UserStatus status);
}
