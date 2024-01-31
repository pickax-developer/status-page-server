package com.pickax.status.page.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pickax.status.page.server.domain.model.Site;

public interface SiteRepository extends JpaRepository<Site, Long>, SiteRepositoryCustom {
	Optional<Site> findByUserId(Long userId);
}
