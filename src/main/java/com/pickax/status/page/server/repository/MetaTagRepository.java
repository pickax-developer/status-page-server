package com.pickax.status.page.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pickax.status.page.server.domain.MetaTag;

public interface MetaTagRepository extends JpaRepository<MetaTag, Long> {
}
