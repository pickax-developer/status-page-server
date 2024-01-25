package com.pickax.status.page.server.common.event;

import com.pickax.status.page.server.domain.enumclass.ComponentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ComponentStatusInspectedEvent {

    private ComponentStatus currentComponentStatus;

    private ComponentStatus changedComponentStatus;

    private String siteName;

    private String componentName;

    private String username;

    @Builder
    public ComponentStatusInspectedEvent(
            ComponentStatus currentComponentStatus, ComponentStatus changedComponentStatus, String siteName, String componentName, String username
    ) {
        this.currentComponentStatus = currentComponentStatus;
        this.changedComponentStatus = changedComponentStatus;
        this.siteName = siteName;
        this.componentName = componentName;
        this.username = username;
    }

}
