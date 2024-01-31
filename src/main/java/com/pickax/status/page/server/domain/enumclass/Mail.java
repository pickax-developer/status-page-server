package com.pickax.status.page.server.domain.enumclass;

import lombok.Getter;

public enum Mail {
	COMPONENT_STATUS_NOTIFICATION("MAIL_TITLE_OF_COMPONENT_STATUS_NOTIFICATION", "COMPONENT_STATUS_NOTIFICATION"),
	SIGNUP_EMAIL_AUTHENTICATION_CODE("MAIL_TITLE_OF_SIGNUP_EMAIL_AUTHENTICATION_CODE", "SIGNUP_EMAIL_AUTHENTICATION_CODE");

	@Getter
	private String subject;
	@Getter
	private String template;

	Mail(String subject, String template) {
		this.subject = subject;
		this.template = template;
	}
}
