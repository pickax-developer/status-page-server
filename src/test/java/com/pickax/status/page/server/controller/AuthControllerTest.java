package com.pickax.status.page.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pickax.status.page.server.dto.request.auth.EmailAuthSendRequestDto;
import com.pickax.status.page.server.dto.request.auth.EmailAuthVerifyRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.pickax.status.page.server.common.exception.ErrorCode.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:data/email_authentications.sql", "classpath:data/users.sql"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthControllerTest {
    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST 회원가입 이메일 인증 코드 발송 - 성공 200 OK")
    void sendEmailAuthenticationCodeForSignup() throws Exception {
        // given
        String url = "/auth/email-auth";
        EmailAuthSendRequestDto requestDto = new EmailAuthSendRequestDto("codeuser@ruu.kr");

        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())

                // then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST 회원가입 이메일 인증 코드 발송 - 이미 존재하는 유저일 경우 409 CONFLICT")
    void sendEmailAuthenticationCodeForSignupByAlreadyExistentEmail() throws Exception {
        // given
        String url = "/auth/email-auth";
        EmailAuthSendRequestDto requestDto = new EmailAuthSendRequestDto("user1@ruu.kr");

        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())

                // then
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.customError").value(DUPLICATE_USER.name()));
    }

    @Test
    @DisplayName("POST 회원가입 이메일 인증 코드 검증 - 검증 성공 200 OK")
    void verifyEmailAuthenticationCodeForSignup() throws Exception {
        // given
        String url = "/auth/email-auth/verify";
        EmailAuthVerifyRequestDto requestDto = new EmailAuthVerifyRequestDto("validuser1@ruu.kr", "098677");

        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())

                // then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST 회원가입 이메일 인증 코드 검증 - 존재하지 않는 email 인증 정보일 경우 404 NOT_FOUND")
    void verifyEmailAuthenticationCodeForSignupByNonExistentEmail() throws Exception {
        // given
        String url = "/auth/email-auth/verify";
        EmailAuthVerifyRequestDto requestDto = new EmailAuthVerifyRequestDto("nonexistentuser@ruu.kr", "098677");

        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())

                // then
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.customError").value(NOT_FOUND_AUTHENTICATION_CODE.name()));
    }

    @Test
    @DisplayName("POST 회원가입 이메일 인증 코드 검증 - 유효 기간이 지난 경우 404 NOT_FOUND")
    void verifyEmailAuthenticationCodeForSignupOnExpiredDate() throws Exception {
        // given
        String url = "/auth/email-auth/verify";
        EmailAuthVerifyRequestDto requestDto = new EmailAuthVerifyRequestDto("invaliduser2@ruu.kr", "345334");

        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())

                // then
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.customError").value(NOT_FOUND_AUTHENTICATION_CODE.name()));
    }

    @Test
    @DisplayName("POST 회원가입 이메일 인증 코드 검증 - 인증 번호가 틀린 경우 400 BAD_REQUEST")
    void verifyEmailAuthenticationCodeForSignupByInvalidCode() throws Exception {
        // given
        String url = "/auth/email-auth/verify";
        EmailAuthVerifyRequestDto requestDto = new EmailAuthVerifyRequestDto("validuser1@ruu.kr", "123456");

        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())

                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.customError").value(INVALID_AUTHENTICATION_CODE.name()));
    }
}