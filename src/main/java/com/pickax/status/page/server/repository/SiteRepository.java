package com.pickax.status.page.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pickax.status.page.server.domain.model.Site;

public interface SiteRepository extends JpaRepository<Site, Long>, SiteRepositoryCustom {
	Site findByUserId(Long userId);
}
