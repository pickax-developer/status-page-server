package com.pickax.status.page.server.service;

import com.pickax.status.page.server.domain.enumclass.ComponentStatus;
import com.pickax.status.page.server.domain.model.ComponentStatusLog;
import com.pickax.status.page.server.dto.LatestHealthCheckCallLogDto;
import com.pickax.status.page.server.repository.*;
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
    private final StatusLogRepository statusLogRepository;
    private final HealthCheckCallLogRepositoryQuery healthCheckCallLogRepositoryQuery;

    @Transactional
    public void inspectHealthCheckCall() {
        List<LatestHealthCheckCallLogDto> latestHealthCheckCallLogs = healthCheckCallLogRepositoryQuery.findLatestLogsByComponentId();
        ComponentStatus componentStatus = ComponentStatus.NONE;

        for (LatestHealthCheckCallLogDto latestHealthCheckLog : latestHealthCheckCallLogs) {
            LocalDateTime lastRequestDateTime = latestHealthCheckLog.getLatestRequestDateTime().toLocalDateTime();
            LocalDateTime timeLimit = lastRequestDateTime.plusSeconds(latestHealthCheckLog.getFrequency());
            LocalDateTime now = LocalDateTime.now();

            if (now.isBefore(timeLimit) || now.isEqual(timeLimit)) {
                componentStatus = ComponentStatus.NO_ISSUES;
                saveStatusLog(latestHealthCheckLog, lastRequestDateTime, 0L, now, componentStatus);
            } else {
                Long riskLevel = this.statusLogRepository.findLatestComponentRiskLevel(latestHealthCheckLog.getComponentId());
                componentStatus = makeComponentStatusByRiskLevel(riskLevel + 1);
                log.debug("risk level is {}, component status is {}", riskLevel + 1, componentStatus);

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

        return ComponentStatus.OUTAGE;
    }

    private void saveStatusLog(LatestHealthCheckCallLogDto latestHealthCheckLog, LocalDateTime lastRequestDateTime, Long riskLevel, LocalDateTime now, ComponentStatus componentStatus) {
        this.statusLogRepository.save(ComponentStatusLog.create(
                latestHealthCheckLog.getComponentId(),
                lastRequestDateTime,
                latestHealthCheckLog.getFrequency(),
                componentStatus,
                riskLevel,
                now
        ));
    }

}
