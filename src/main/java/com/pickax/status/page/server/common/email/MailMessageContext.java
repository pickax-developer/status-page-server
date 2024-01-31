package com.pickax.status.page.server.common.email;

import com.pickax.status.page.server.domain.enumclass.Mail;
import lombok.Builder;
import lombok.Getter;
import org.thymeleaf.context.Context;

import java.util.Locale;

@Getter
@Builder
public class MailMessageContext {
	private final Context context;
	private final Mail mailType;
	private final String to;
	private final Locale locale = Locale.ENGLISH;
	private final String[] subjectArguments;
}
