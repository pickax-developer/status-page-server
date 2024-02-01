package com.pickax.status.page.server.service;

import com.pickax.status.page.server.common.email.EmailSender;
import com.pickax.status.page.server.common.email.MailMessageContext;
import com.pickax.status.page.server.common.event.ComponentStatusInspectedEvent;
import com.pickax.status.page.server.common.event.UserResignEvent;
import com.pickax.status.page.server.domain.enumclass.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailSender emailSender;

    public void sendComponentStatusChangedNotifyEmail(ComponentStatusInspectedEvent event) {
        // TODO. User 도메인이 만들어지면 해당 사이트 소유자 이메일로 메일을 전송합니다.
        String receiverEmail = "your_email@gmail.com";
        MailMessageContext messageContext = createComponentStatusChangedNotifyEmailContext(event, receiverEmail);
        emailSender.send(messageContext);
    }

    private MailMessageContext createComponentStatusChangedNotifyEmailContext(
            ComponentStatusInspectedEvent event, String receiverEmail
    ) {
        Context context = new Context();
        context.setVariable("notification", event);
        return MailMessageContext.builder()
                .context(context)
                .mailType(Mail.COMPONENT_STATUS_NOTIFICATION)
                .to(receiverEmail)
                .build();
    }

    public void sendEmailAuthenticationCodeForSignup(String receiverEmail, String code) {
        MailMessageContext messageContext = createEmailAuthenticationCodeForSignupEmailContext(receiverEmail, code);
        emailSender.send(messageContext);
    }

    private MailMessageContext createEmailAuthenticationCodeForSignupEmailContext(String receiverEmail, String code) {
        Context context = new Context();
        context.setVariable("receiverEmail", receiverEmail);
        context.setVariable("code", code);
        return MailMessageContext.builder()
                .context(context)
                .mailType(Mail.SIGNUP_EMAIL_AUTHENTICATION_CODE)
                .to(receiverEmail)
                .build();
    }

    public void sendUserResignEmail(UserResignEvent userResignEvent) {
        MailMessageContext messageContext = createUserResignEmailContext(userResignEvent);
        emailSender.send(messageContext);
    }

    private MailMessageContext createUserResignEmailContext(UserResignEvent userResignEvent) {
        Context context = new Context();
        context.setVariable("receiverEmail", userResignEvent.email());
        return MailMessageContext.builder()
            .context(context)
            .mailType(Mail.USER_RESIGN)
            .to(userResignEvent.email())
            .build();
    }
}
