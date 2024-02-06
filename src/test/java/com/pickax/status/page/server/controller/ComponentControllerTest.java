package com.pickax.status.page.server.controller;

import static com.pickax.status.page.server.common.exception.ErrorCode.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pickax.status.page.server.security.dto.AccessTokenResponseDto;
import com.pickax.status.page.server.security.jwt.TokenProvider;
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

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Sql(scripts = {
	"classpath:data/users.sql",
	"classpath:data/sites.sql",
	"classpath:data/components.sql"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ComponentControllerTest {
	@Autowired
	public MockMvc mockMvc;

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
	@DisplayName("GET active component 리스트 조회 api - 200 OK")
	void getActiveComponents() throws Exception {
		// when
		Long siteId = 1L;
		String url = String.format("/sites/%s/components/active", siteId);

		mockMvc.perform(
				MockMvcRequestBuilders
					.get(url)
					.contentType(MediaType.APPLICATION_JSON)
					.header(HttpHeaders.AUTHORIZATION, getAuthorizationBearerToken(accessTokenResponseDto))
			)
			.andDo(print())

			// then
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.componentActiveResponseDtoList.size()").value("5"))
			.andExpect(jsonPath("$.lastUpdatedDate").value("2022-12-08T11:44:30.327959"));
	}

	@Test
	@DisplayName("GET active component 리스트 조회 api - 404 NOT FOUND")
	void getActiveComponentsByNonExistentSiteId() throws Exception {
		// when
		Long siteId = 99L;
		String url = String.format("/sites/%s/components/active", siteId);

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
	@DisplayName("GET component 리스트 조회 api - 200 OK")
	void getComponents() throws Exception {
		// when
		Long siteId = 1L;
		String url = String.format("/sites/%s/components", siteId);

		mockMvc.perform(
				MockMvcRequestBuilders
					.get(url)
					.contentType(MediaType.APPLICATION_JSON)
					.header(HttpHeaders.AUTHORIZATION, getAuthorizationBearerToken(accessTokenResponseDto))
			)
			.andDo(print())

			// then
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.componentResponseDtoList.size()").value("6"));
	}

	@Test
	@DisplayName("GET component 리스트 조회 api - 404 NOT FOUND")
	void getComponentsByNonExistentSiteId() throws Exception {
		// when
		Long siteId = 99L;
		String url = String.format("/sites/%s/components", siteId);

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
