package com.dbexp.db_experiment.controller;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public abstract class BaseControllerTest {

    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper;

    @BeforeEach
    void setUpBase() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    protected ResultActions performGetRequest(String url, Object request) throws Exception {
        return mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

    protected ResultActions performPostRequest(String url, Object request) throws Exception {
        return mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

    protected ResultActions performPutRequest(String url, Object request) throws Exception {
        return mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

    protected ResultActions performDeleteRequest(String url, Object request) throws Exception {
        return mockMvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

    protected void assertSuccessResponse(ResultActions result) throws Exception {
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").exists());
    }

    protected void assertCreatedResponse(ResultActions result) throws Exception {
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").exists());
    }

    protected void assertBadRequestWithMessage(ResultActions result, String message) throws Exception {
        result.andExpect(status().isBadRequest())
                .andExpect(content().string(message));
    }

    protected void assertBadRequest(ResultActions result) throws Exception {
        result.andExpect(status().isBadRequest());
    }

    protected void assertInternalServerError(ResultActions result, String message) throws Exception {
        result.andExpect(status().isInternalServerError())
                .andExpect(content().string(message));
    }
}