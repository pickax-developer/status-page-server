package com.pickax.status.page.server.repository;

import com.pickax.status.page.server.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
}
