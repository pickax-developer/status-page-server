package com.pickax.status.page.server.service;

import com.pickax.status.page.server.domain.enumclass.ComponentStatus;
import com.pickax.status.page.server.domain.model.StatusLog;
import com.pickax.status.page.server.dto.reseponse.LatestHealthCheckRequestLogDto;
import com.pickax.status.page.server.repository.ComponentRepository;
import com.pickax.status.page.server.repository.HealthCheckRepository;
import com.pickax.status.page.server.repository.StatusLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusLogService {

    private final ComponentRepository componentRepository;
    private final HealthCheckRepository healthCheckRepository;
    private final StatusLogRepository statusLogRepository;

    @Transactional
    public void inspectHealthCheckRequest() {
        List<LatestHealthCheckRequestLogDto> latestHealthCheckRequestLogs = healthCheckRepository.findLatestLogsByComponentId();
        ComponentStatus componentStatus = ComponentStatus.NONE;

        for (LatestHealthCheckRequestLogDto latestHealthCheckLog : latestHealthCheckRequestLogs) {
            LocalDateTime lastRequestDateTime = latestHealthCheckLog.getLatestRequestDate().toLocalDateTime();
            LocalDateTime timeLimit = lastRequestDateTime.plusSeconds(latestHealthCheckLog.getFrequency());
            LocalDateTime now = LocalDateTime.now();

            if (now.isBefore(timeLimit)) {
                componentStatus = ComponentStatus.NO_ISSUES;
                saveStatusLog(latestHealthCheckLog, lastRequestDateTime, 0L, now, componentStatus);
            } else {
                Long riskLevel = this.statusLogRepository.findLatestComponentRiskLevel(latestHealthCheckLog.getComponentId());
                componentStatus = makeComponentStatusByRiskLevel(riskLevel);
                log.debug("risk level is {}, component status is {}", riskLevel, componentStatus);

                saveStatusLog(latestHealthCheckLog, lastRequestDateTime, riskLevel + 1, now, componentStatus);
            }

            componentRepository.updateComponentStatus(latestHealthCheckLog.getComponentId(), componentStatus);
        }

    }

    private ComponentStatus makeComponentStatusByRiskLevel(Long riskLevel) {
        if (0 <= riskLevel && riskLevel < 5) {
            return ComponentStatus.NO_ISSUES;
        }
        if (5 <= riskLevel && riskLevel < 10) {
            return ComponentStatus.WARN;
        }
        if (riskLevel >= 10) {
            return ComponentStatus.OUTAGE;
        }

        return ComponentStatus.NONE;
    }

    private void saveStatusLog(LatestHealthCheckRequestLogDto latestHealthCheckLog, LocalDateTime lastRequestDateTime, Long riskLevel, LocalDateTime now, ComponentStatus componentStatus) {
        this.statusLogRepository.save(StatusLog.create(
                latestHealthCheckLog.getComponentId(),
                lastRequestDateTime,
                latestHealthCheckLog.getFrequency(),
                componentStatus,
                riskLevel,
                now
        ));
    }

}
