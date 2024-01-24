package com.pickax.status.page.server.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import com.pickax.status.page.server.security.jwt.JwtAccessDeniedHandler;
import com.pickax.status.page.server.security.jwt.JwtAuthenticationEntryPoint;
import com.pickax.status.page.server.security.jwt.JwtFilter;
import com.pickax.status.page.server.security.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CorsConfigurationSource customCorsConfigurationSource;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final TokenProvider tokenProvider;

	private JwtFilter jwtFilter() {
		return new JwtFilter(tokenProvider);
	}


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors(cors -> cors.configurationSource(customCorsConfigurationSource))
			.csrf(AbstractHttpConfigurer::disable)
			.exceptionHandling(exception -> exception
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler)
			)
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/sites/**", "/components/**", "/health-check/**").permitAll()
				.anyRequest().authenticated()
			)
			.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
