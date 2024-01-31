package com.pickax.status.page.server.security.jwt;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pickax.status.page.server.security.dto.AccessTokenResponseDto;
import com.pickax.status.page.server.security.service.CustomUserDetails;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenProvider implements Serializable {
	public static final String ISS = "QUACK";
	@Value("${security.jwt-config.secret}")
	private String secret;
	@Value("${security.jwt-config.access-token-expire}")
	private long accessTokenExpireTime;

	public AccessTokenResponseDto createAccessToken(Authentication authentication) {
		Date now = new Date();
		Map<String, Object> headers = new HashMap<>();
		headers.put("typ", "JWT");
		headers.put("alg", "HS512");

		Date accessTokenExpiresIn = new Date(now.getTime() + accessTokenExpireTime);

		log.info("[CREATE_ACCESS_TOKEN] - NOW: {}, EXP: {}", now, accessTokenExpiresIn);

		String accessToken = Jwts.builder()
				.setSubject(authentication.getName())
				.setHeader(headers)
				.setIssuer(ISS)
				.setIssuedAt(now)
				.setExpiration(accessTokenExpiresIn)
				.signWith(SignatureAlgorithm.HS256, getJwtSecret())
				.compact();

		return AccessTokenResponseDto.builder()
				.accessToken(accessToken)
				.accessTokenExpiresIn(accessTokenExpiresIn)
				.build();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(getJwtSecret()).build().parseClaimsJws(token);
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

	public Authentication getAuthentication(String accessToken) {
		Claims claims = parseClaims(accessToken);

		UserDetails principal = new CustomUserDetails(Long.parseLong(claims.getSubject()), "");
		return new UsernamePasswordAuthenticationToken(principal, "", null);
	}

	private Claims parseClaims(String accessToken) {
		try {
			return Jwts
					.parserBuilder()
					.setSigningKey(getJwtSecret())
					.build()
					.parseClaimsJws(accessToken)
					.getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
}
