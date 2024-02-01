package com.pickax.status.page.server.service.slack;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Profile("!prod")
@Slf4j
public class MockErrorAlertService implements ErrorAlertService {
	@Override
	public void sendErrorMessage(Exception exception, String message, HttpStatus httpStatus) {
		log.info("MockSlackServiceImpl.sendErrorMessage");
	}
}
