package com.pickax.status.page.server.common;

import com.pickax.status.page.server.common.event.ComponentStatusInspectedEvent;
import com.pickax.status.page.server.common.exception.CustomException;
import com.pickax.status.page.server.common.exception.ErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendComponentStatusChangedNotifyEmail(ComponentStatusInspectedEvent event) {
        MimeMessage message = javaMailSender.createMimeMessage();
        boolean hasImage = false;
        final String ENCODING = "UTF-8";

        String emailSubject = "[QUACK_RUN] 컴포넌트 상태 변경 알림입니다.";
        String htmlTemplate = "component_status_notification.html";
        // TODO. User 도메인이 만들어지면 해당 사이트 소유자 이메일로 메일을 전송합니다.
        String receiverEmail = "your_email@gmail.com";
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, hasImage, ENCODING);
            messageHelper.setSubject(emailSubject);
            messageHelper.setTo(receiverEmail);

            Context context = createContext(event);
            String text = templateEngine.process(htmlTemplate, context);
            messageHelper.setText(text, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private Context createContext(ComponentStatusInspectedEvent event) {
        Context context = new Context();
        context.setVariable("notification", event);
        return context;
    }

}
