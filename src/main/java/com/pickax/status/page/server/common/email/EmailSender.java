package com.pickax.status.page.server.common.email;

public interface EmailSender {
    void send(MailMessageContext messageContext);
}
