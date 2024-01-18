package com.pickax.status.page.server.domain.model;

import com.pickax.status.page.server.domain.enumclass.ComponentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Entity
@Table(name = "status_logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StatusLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "component_id", nullable = false)
    private Long componentId;

    @Column(name = "request_at", nullable = false)
    private LocalDateTime requestDateTime;

    @Column(name = "frequency", nullable = false)
    private Long frequency;

    @Enumerated(EnumType.STRING)
    @Column(name = "component_status", nullable = false)
    private ComponentStatus componentStatus;

    @Column(name = "riskLevel", nullable = false)
    private Long riskLevel;

    @Column(name = "scheduler_run_at", nullable = false)
    private LocalDateTime schedulerRunDateTime;


    public StatusLog(Long componentId, LocalDateTime requestDateTime, Long frequency, ComponentStatus componentStatus, Long riskLevel, LocalDateTime schedulerRunDateTime) {
        this.componentId = componentId;
        this.requestDateTime = requestDateTime;
        this.frequency = frequency;
        this.componentStatus = componentStatus;
        this.riskLevel = riskLevel;
        this.schedulerRunDateTime = schedulerRunDateTime;
    }

    public static StatusLog create(Long componentId, LocalDateTime lastRequestDateTime, Long frequency, ComponentStatus componentStatus, Long riskLevel, LocalDateTime schedulerRunDateTime) {
        return new StatusLog(componentId, lastRequestDateTime, frequency, componentStatus, riskLevel, schedulerRunDateTime);
    }

}
