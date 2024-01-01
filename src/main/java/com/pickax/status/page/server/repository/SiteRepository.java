package com.pickax.status.page.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pickax.status.page.server.domain.model.Site;
import java.util.List;

public interface SiteRepository extends JpaRepository<Site, Long> {

    List<Site> findAllByUserId(Long loggedInUserId);
}
