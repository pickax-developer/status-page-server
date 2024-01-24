package com.pickax.status.page.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pickax.status.page.server.common.exception.ErrorCode;
import com.pickax.status.page.server.dto.request.HealthCheckCallRequestDto;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Sql(scripts = {
        "classpath:data/users.sql", "classpath:data/sites.sql", "classpath:data/components.sql"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HealthCheckControllerTest {
    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST health check 요청 - 성공 200 OK")
    void call() throws Exception {
        // given
        HealthCheckCallRequestDto healthCheckCallRequestDto = new HealthCheckCallRequestDto(1L);
        String url = "/health-check";

        // when
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post(url)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer qewafasf")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(healthCheckCallRequestDto))
        )
                .andDo(print())

                // then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST health check 요청 - 존재하지 않는 Component 일 경우 404 NOT_FOUND")
    void callByNonExistentComponentId() throws Exception {
        // given
        HealthCheckCallRequestDto healthCheckCallRequestDto = new HealthCheckCallRequestDto(9999999999L);
        String url = "/health-check";

        // when
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post(url)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer qewafasf")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(healthCheckCallRequestDto))
        )
                .andDo(print())

                // then
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.customError").value(ErrorCode.NOT_FOUND_COMPONENT.name()));
    }

    @Test
    @DisplayName("POST health check 요청 - secret key 가 일치하지 않을 경우 401 UNAUTHORIZED")
    void callByInvalidSecretKey() throws Exception {
        // given
        HealthCheckCallRequestDto healthCheckCallRequestDto = new HealthCheckCallRequestDto(1L);
        String url = "/health-check";

        // when
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post(url)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer testFailedSecretKey")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(healthCheckCallRequestDto))
        )
                .andDo(print())

                // then
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.customError").value(ErrorCode.INVALID_SECRET_KEY.name()));
    }
}
