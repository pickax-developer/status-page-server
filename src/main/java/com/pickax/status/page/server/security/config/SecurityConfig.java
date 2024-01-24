package com.pickax.status.page.server.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final CorsConfigurationSource customCorsConfigurationSource;

	public SecurityConfig(CorsConfigurationSource customCorsConfigurationSource) {
		this.customCorsConfigurationSource = customCorsConfigurationSource;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors(cors -> cors.configurationSource(customCorsConfigurationSource))
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/sites/**", "/components/**", "/health-check/**").permitAll()
				.anyRequest().authenticated()
			);
		return http.build();
	}
}
