package com.pickax.status.page.server.dto.reseponse;

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

    private Timestamp latestRequestDate;

    private Long frequency;


    public LatestHealthCheckCallLogDto(Long healthCheckCallLogId, Long componentId, Timestamp latestRequestDate, Long frequency) {
        this.healthCheckCallLogId = healthCheckCallLogId;
        this.componentId = componentId;
        this.latestRequestDate = latestRequestDate;
        this.frequency = frequency;
    }
}
