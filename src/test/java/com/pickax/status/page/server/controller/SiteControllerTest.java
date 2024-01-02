package com.pickax.status.page.server.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pickax.status.page.server.dto.request.SiteRequestDto;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SiteControllerTest {
	@Autowired
	public MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("POST site 등록 api - 200 OK")
	void createSite() throws Exception {
		// when
		String url = "/sites";

		SiteRequestDto siteRequestDto = SiteRequestDto.of(
			"name",
			"description",
			"url"
		);

		mockMvc.perform(
				MockMvcRequestBuilders
					.post(url)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(siteRequestDto))
					.accept(MediaType.APPLICATION_JSON)
			)
			.andDo(print())

			// then
			.andExpect(status().isOk());
	}
}
