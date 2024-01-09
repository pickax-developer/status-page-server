package com.pickax.status.page.server.log;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class Logging {

	@Around("within(com.pickax.status.page.server..*)")
	public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		long startAt = System.currentTimeMillis();

		log.info("REQUEST : {}.{} = param : {}",
			proceedingJoinPoint.getSignature().getDeclaringTypeName(),
			proceedingJoinPoint.getSignature().getName(), params(proceedingJoinPoint)
		);

		Object result = proceedingJoinPoint.proceed();

		long endAt = System.currentTimeMillis();
		log.info("RESPONSE : {}.{} = result : {} ({}ms)",
			proceedingJoinPoint.getSignature().getDeclaringTypeName(),
			proceedingJoinPoint.getSignature().getName(), result, endAt - startAt
		);

		return result;
	}

	public Map<String, Object> params(ProceedingJoinPoint proceedingJoinPoint) {
		CodeSignature codeSignature = (CodeSignature)proceedingJoinPoint.getSignature();
		String[] parameterNames = codeSignature.getParameterNames();
		Object[] args = proceedingJoinPoint.getArgs();
		Map<String, Object> params = new HashMap<>();
		for (int i = 0; i < parameterNames.length; i++) {
			params.put(parameterNames[i], args[i]);
		}
		return params;
	}
}
