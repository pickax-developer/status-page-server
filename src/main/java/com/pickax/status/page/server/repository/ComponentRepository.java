package com.pickax.status.page.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pickax.status.page.server.domain.model.Component;

public interface ComponentRepository extends JpaRepository<Component, Long> {
}
