package com.pickax.status.page.server.common.event.handler;

import com.pickax.status.page.server.common.event.ComponentStatusInspectedEvent;
import com.pickax.status.page.server.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailEventHandler {

    private final EmailService emailService;

    @EventListener
    public void sendEmail(ComponentStatusInspectedEvent event) {
        emailService.sendComponentStatusChangedNotifyEmail(event);
        log.debug("[{}-{}] 컴포넌트 상태 변경 메일 발송", event.getSiteName(), event.getComponentName());
    }
}
