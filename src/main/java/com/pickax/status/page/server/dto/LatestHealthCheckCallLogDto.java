package com.pickax.status.page.server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@ToString
@NoArgsConstructor
public class LatestHealthCheckCallLogDto {

    private Long healthCheckCallLogId;

    private Long componentId;

    private Timestamp latestRequestDateTime;

    private Long frequency;

    private String componentStatus;

    private String componentName;

    private String siteName;

    public LatestHealthCheckCallLogDto(Long healthCheckCallLogId, Long componentId, Timestamp latestRequestDateTime, Long frequency, String componentStatus, String componentName, String siteName) {
        this.healthCheckCallLogId = healthCheckCallLogId;
        this.componentId = componentId;
        this.latestRequestDateTime = latestRequestDateTime;
        this.frequency = frequency;
        this.componentStatus = componentStatus;
        this.componentName = componentName;
        this.siteName = siteName;
    }
}
