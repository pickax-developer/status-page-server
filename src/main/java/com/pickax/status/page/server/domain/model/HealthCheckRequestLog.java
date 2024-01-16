package com.pickax.status.page.server.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "health_check_request_logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HealthCheckRequestLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "component_id", nullable = false)
    private Long componentId;

    @Column(name = "request_at", nullable = false)
    private LocalDateTime requestDate;
}
