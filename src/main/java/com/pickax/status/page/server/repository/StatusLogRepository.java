package com.pickax.status.page.server.repository;

import com.pickax.status.page.server.domain.model.ComponentStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusLogRepository extends JpaRepository<ComponentStatusLog, Long>, StatusLogRepositoryCustom {

}
