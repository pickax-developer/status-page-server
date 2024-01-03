package com.pickax.status.page.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pickax.status.page.server.domain.model.MetaTag;

import java.util.Optional;

public interface MetaTagRepository extends JpaRepository<MetaTag, Long> {
    Optional<MetaTag> findFirstBySite_Id(long id);
}
