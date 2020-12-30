package com.github.epidemicsimulator.adapter.in.web;

import com.github.epidemicsimulator.application.port.in.GenerateSimulationUseCase;
import com.github.epidemicsimulator.application.port.in.ModifySimulationUseCase;
import com.github.epidemicsimulator.application.port.out.LoadSimulationPort;
import com.github.epidemicsimulator.application.port.out.RemoveSimulationQuery;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SimulationController.class)
class DefaultRestExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoadSimulationPort loadSimulationPort;
    @MockBean
    private GenerateSimulationUseCase generateSimulationUseCase;
    @MockBean
    private ModifySimulationUseCase modifySimulationUseCase;
    @MockBean
    private RemoveSimulationQuery removeSimulationQuery;

    private static String BASE_PATH = "http://localhost:8080";
    private static String SIMULATIONS_PATH = "/api/simulations/";
    private static final Long ID = 1L;

    @Test
    public void shouldHaveNotFoundStatusOnNoHandlerForHttpRequest() throws Exception {
        mockMvc.perform(get(BASE_PATH + "invalid/xx/"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("errors[0]", containsString("No handler found for") ));
    }

    @Test
    public void shouldHaveBadRequestStatusOnMethodArgumentMismatch() throws Exception {
        mockMvc.perform(get(BASE_PATH + SIMULATIONS_PATH + "badArgument"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors[0]", containsString("should be of type") ));
    }

    @Test
    public void shouldThrowMethodNotSupported() throws Exception {
        mockMvc.perform(patch(BASE_PATH + SIMULATIONS_PATH + ID ))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("errors[0]",
                        containsString("method is not supported for this request. Supported methods are") ));
    }

    @Test
    public void shouldThrowMediaTypeNotSupported() throws Exception {
        mockMvc.perform(post(BASE_PATH + SIMULATIONS_PATH)
                .contentType(MediaType.APPLICATION_XML))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(jsonPath("errors[0]",
                        containsString("media type is not supported. Supported media types are") ));
    }
}