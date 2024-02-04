package com.pickax.status.page.server.controller;

import static com.pickax.status.page.server.common.exception.ErrorCode.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pickax.status.page.server.dto.request.SiteCreateRequestDto;
import com.pickax.status.page.server.security.dto.AccessTokenResponseDto;
import com.pickax.status.page.server.security.jwt.TokenProvider;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Sql(scripts = {
	"classpath:data/users.sql",
	"classpath:data/sites.sql",
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SiteControllerTest {
	@Autowired
	public MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TokenProvider tokenProvider;

	private AccessTokenResponseDto accessTokenResponseDto;

	private String getAuthorizationBearerToken(AccessTokenResponseDto accessTokenResponseDto) {
		return "Bearer " + accessTokenResponseDto.getAccessToken();
	}

	@BeforeAll
	void setUp() {
		userSetup();
	}

	void userSetup() {
		Long userId = 1L;
		String email = "user1@ruu.kr";
		accessTokenResponseDto = tokenProvider.createAccessToken(userId);
	}

	@Test
	@DisplayName("POST site 등록 api - 200 OK")
	void createSite() throws Exception {
		// when
		String url = "/sites";

		SiteCreateRequestDto siteCreateRequestDto = SiteCreateRequestDto.of(
			"name",
			"description",
			"url"
		);

		mockMvc.perform(
				MockMvcRequestBuilders
					.post(url)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(siteCreateRequestDto))
					.accept(MediaType.APPLICATION_JSON)
					.header(HttpHeaders.AUTHORIZATION, getAuthorizationBearerToken(accessTokenResponseDto))
			)
			.andDo(print())

			// then
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("GET site secretKey 조회 api - 200 OK")
	void getSecretKey() throws Exception {
		// when
		Long siteId = 1L;
		String url = String.format("/sites/%s/secret-key", siteId);

		mockMvc.perform(
				MockMvcRequestBuilders
					.get(url)
					.contentType(MediaType.APPLICATION_JSON)
					.header(HttpHeaders.AUTHORIZATION, getAuthorizationBearerToken(accessTokenResponseDto))
			)
			.andDo(print())

			// then
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("GET site secretKey 조회 api - 404 NOT FOUND")
	void getSecretKeyByNonExistentSiteId() throws Exception {
		// when
		Long siteId = 99L;
		String url = String.format("/sites/%s/secret-key", siteId);

		mockMvc.perform(
				MockMvcRequestBuilders
					.get(url)
					.contentType(MediaType.APPLICATION_JSON)
					.header(HttpHeaders.AUTHORIZATION, getAuthorizationBearerToken(accessTokenResponseDto))
			)
			.andDo(print())

			// then
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.customError").value(NOT_FOUND_SITE.name()));
	}

	@Test
	@DisplayName("GET site 상세 조회 api - 성공 200 OK")
	void getSite() throws Exception {
		// when
		Long siteId = 1L;
		String url = String.format("/sites/%s", siteId);

		mockMvc.perform(
				MockMvcRequestBuilders
					.get(url)
					.contentType(MediaType.APPLICATION_JSON)
					.header(HttpHeaders.AUTHORIZATION, getAuthorizationBearerToken(accessTokenResponseDto))
			)
			.andDo(print())

			// then
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.name").value("1 name"))
			.andExpect(jsonPath("$.description").value("1 description"))
			.andExpect(jsonPath("$.url").value("http://dasfas"))
			.andExpect(jsonPath("$.status").value("COMPLETED"));
	}

	@Test
	@DisplayName("GET site 상세 조회 api - 존재하지 않는 경우 404 NOT_FOUND")
	void getSiteByNonExistentSiteId() throws Exception {
		// when
		String url = String.format("/sites/%s", 9999999999L);

		mockMvc.perform(
				MockMvcRequestBuilders
					.get(url)
					.contentType(MediaType.APPLICATION_JSON)
					.header(HttpHeaders.AUTHORIZATION, getAuthorizationBearerToken(accessTokenResponseDto))
			)
			.andDo(print())

			// then
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.customError").value(NOT_FOUND_SITE.name()));
	}
}
