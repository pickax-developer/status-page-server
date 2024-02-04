package com.pickax.status.page.server.repository;

import com.pickax.status.page.server.domain.model.EmailAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmailAuthenticationRepository extends JpaRepository<EmailAuthentication, Long> {
    List<EmailAuthentication> findAllByEmail(String email);

    Optional<EmailAuthentication> findFirst1ByEmail(String email);
}
