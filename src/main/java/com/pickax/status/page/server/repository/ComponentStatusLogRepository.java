package com.pickax.status.page.server.repository;

import com.pickax.status.page.server.domain.model.ComponentStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentStatusLogRepository extends JpaRepository<ComponentStatusLog, Long>, ComponentStatusLogRepositoryCustom {

}
