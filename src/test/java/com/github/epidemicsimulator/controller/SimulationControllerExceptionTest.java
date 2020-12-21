package com.github.epidemicsimulator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.epidemicsimulator.domain.Simulation;
import com.github.epidemicsimulator.domain.SimulationResult;
import com.github.epidemicsimulator.exception.SimulationNotFoundException;
import com.github.epidemicsimulator.service.SimulationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SimulationController.class)
class SimulationControllerExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SimulationService service;

    private static String BASE_PATH = "http://localhost:8080/simulations";
    private static final Long ID = 1L;

    @Test
    public void shouldThrowNotFoundException() throws Exception {
        Long invalidId = -1L;
        String expected = new SimulationNotFoundException(invalidId).getMessage();

        mockMvc.perform(get(BASE_PATH + "/" + invalidId ))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof SimulationNotFoundException))
                .andExpect(result -> assertEquals(expected, result.getResolvedException().getMessage()));
    }

    @Test
    public void shouldThrowMethodNotSupportedOnPutMethod() throws Exception {
        mockMvc.perform(put(BASE_PATH + "/" + ID ))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andExpect(result -> assertEquals(new HttpRequestMethodNotSupportedException("PUT").getMessage(),
                                                  result.getResolvedException().getMessage()));
    }

    @Test
    public void shouldThrowNotReadableExceptionOnInvalidRequestBody() throws Exception {
        String request = "some string";
        String responseBody = "JSON parse error";
        mockMvc.perform(post(BASE_PATH, request))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpMessageNotReadableException))
                .andExpect(result -> assertEquals(responseBody, result.getResponse().getContentAsString()));
    }
}