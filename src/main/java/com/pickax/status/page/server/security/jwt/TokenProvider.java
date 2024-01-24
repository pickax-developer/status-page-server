package com.pickax.status.page.server.security.jwt;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenProvider implements Serializable {
	public static final String ISS = "QUACK";
	@Value("${security.jwt-config.secret}")
	private String secret;
	@Value("${security.jwt-config.access-token-expire}")
	private long accessTokenExpireTime;

	public String createAccessToken() {
		Date now = new Date();
		Map<String, Object> headers = new HashMap<>();
		headers.put("typ", "JWT");
		headers.put("alg", "HS512");

		Date accessTokenExpiresIn = new Date(now.getTime() + accessTokenExpireTime);

		log.info("[CREATE_ACCESS_TOKEN] - NOW: {}, EXP: {}", now, accessTokenExpiresIn);

		return Jwts.builder()
			.setHeader(headers)
			.setIssuer(ISS)
			.setIssuedAt(now)
			.setExpiration(accessTokenExpiresIn)
			.signWith(SignatureAlgorithm.HS256, getJwtSecret())
			.compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.info("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.info("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty: {}", e.getMessage());
		} catch (Exception e) {
			log.info("token valid error: {}", e.getMessage());
		}
		return false;
	}

	private String getJwtSecret() {
		return Base64.getEncoder().encodeToString(secret.getBytes());
	}

}
