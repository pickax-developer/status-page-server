package com.pickax.status.page.server.common.event;

import com.pickax.status.page.server.domain.enumclass.ComponentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ComponentStatusInspectedEvent {

    private ComponentStatus previousComponentStatus;

    private ComponentStatus currentComponentStatus;

    private String siteName;

    private String componentName;

    private String username;

    @Builder
    public ComponentStatusInspectedEvent(
            ComponentStatus previousComponentStatus, ComponentStatus currentComponentStatus, String siteName, String componentName, String username
    ) {
        this.previousComponentStatus = previousComponentStatus;
        this.currentComponentStatus = currentComponentStatus;
        this.siteName = siteName;
        this.componentName = componentName;
        this.username = username;
    }

}
