package com.pickax.status.page.server.common;

import com.pickax.status.page.server.common.exception.CustomException;
import com.pickax.status.page.server.common.exception.ErrorCode;
import com.pickax.status.page.server.domain.enumclass.ComponentStatus;
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
    public void sendEmail() {
        MimeMessage message = javaMailSender.createMimeMessage();
        boolean hasImage = false;
        final String ENCODING = "UTF-8";

        // 이메일을 전송하는 데 필요한 데이터들은 추후에 DTO 로 처리할 예정입니다.
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, hasImage, ENCODING);
            messageHelper.setSubject("[QUACK_RUN] 컴포넌트 상태 알림입니다.");
            messageHelper.setTo("kellykang13@gmail.com");

            Context context = createContext();
            String text = templateEngine.process("component_status_notification.html", context);
            messageHelper.setText(text, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private Context createContext() {
        // 데이터를 전달할 클래스 생성 전이므로 테스트용으로 하드코딩해서 사용합니다.
        Context context = new Context();
        context.setVariable("username", "kelly");
        context.setVariable("component", "Notification - 네이버 알림 서버");
        context.setVariable("previousComponentStatus", ComponentStatus.WARN.toString());
        context.setVariable("currentComponentStatus", ComponentStatus.NO_ISSUES.toString());

        return context;
    }

}
