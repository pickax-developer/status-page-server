package com.pickax.status.page.server.common.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Profile(value = "test")
@Component
public class MockEmailSender implements EmailSender {
    @Override
    public void send(MailMessageContext messageContext) {
        log.info("[MockEmailSender - send] messageContext: {}", messageContext);
    }
}
