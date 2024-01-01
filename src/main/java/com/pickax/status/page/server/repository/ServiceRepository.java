package com.pickax.status.page.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pickax.status.page.server.domain.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {

}
