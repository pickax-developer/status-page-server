package com.pickax.status.page.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pickax.status.page.server.domain.model.MetaTag;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import java.util.Optional;

public interface MetaTagRepository extends JpaRepository<MetaTag, Long> {

    @Query("SELECT t FROM MetaTag t WHERE t.site.id = :siteId AND t.isChecked = :notChecked ORDER BY t.expiredDate DESC")
    List<MetaTag> findMetaTagNotYetCheckedBySiteId(Long siteId, boolean notChecked);

	Optional<MetaTag> findFirstBySite_Id(long id);
}
