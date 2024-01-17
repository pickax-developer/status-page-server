package com.pickax.status.page.server.repository;

import com.pickax.status.page.server.domain.model.HealthCheckCallLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthCheckCallLogRepository extends JpaRepository<HealthCheckCallLog, Long> {

}
