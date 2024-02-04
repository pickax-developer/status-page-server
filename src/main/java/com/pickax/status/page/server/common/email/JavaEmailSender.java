package com.pickax.status.page.server.common.email;

import com.pickax.status.page.server.common.exception.CustomException;
import com.pickax.status.page.server.common.exception.ErrorCode;
import io.jsonwebtoken.lang.Objects;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
@Profile(value = {"prod", "local"})
@RequiredArgsConstructor
public class JavaEmailSender implements EmailSender {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final MessageSource messageSource;

    private static final String SYSTEM_EMAIL = "no-reply@quack.run";

    @Async
    public void send(MailMessageContext messageContext) {
        MimeMessage message = javaMailSender.createMimeMessage();
        String subject = getSubject(messageContext);
        String template = messageSource.getMessage(messageContext.getMailType().getTemplate(), null, messageContext.getLocale());
        String text = templateEngine.process(template, messageContext.getContext());
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, false, "UTF-8");
            messageHelper.setFrom(SYSTEM_EMAIL);
            messageHelper.setTo(messageContext.getTo());
            messageHelper.setSubject(subject);
            messageHelper.setText(text, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.MAIL_SEND_ERROR);
        }
    }

    private String getSubject(MailMessageContext messageContext) {
        if (!Objects.isEmpty(messageContext.getSubjectArguments())) {
            return messageSource.getMessage(
                    messageContext.getMailType().getSubject(), messageContext.getSubjectArguments(), messageContext.getLocale());
        }
        return messageSource.getMessage(messageContext.getMailType().getSubject(), null, messageContext.getLocale());
    }
}
