package com.pickax.status.page.server.repository;

import com.pickax.status.page.server.domain.model.StatusLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusLogRepository extends JpaRepository<StatusLog, Long>, StatusLogRepositoryCustom {

}
