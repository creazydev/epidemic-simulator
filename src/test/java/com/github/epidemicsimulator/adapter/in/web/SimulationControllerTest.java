package com.github.epidemicsimulator.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.epidemicsimulator.application.port.in.GenerateSimulationCommand;
import com.github.epidemicsimulator.application.port.in.GenerateSimulationUseCase;
import com.github.epidemicsimulator.application.port.in.ModifySimulationUseCase;
import com.github.epidemicsimulator.application.port.out.LoadSimulationPort;
import com.github.epidemicsimulator.application.port.out.RemoveSimulationQuery;
import com.github.epidemicsimulator.domain.Simulation;
import com.github.epidemicsimulator.domain.SimulationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.Optional;
import java.util.logging.Logger;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SimulationController.class)
class SimulationControllerTest {

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
    Logger logger = Logger.getLogger(this.getClass().getName());


    private static String BASE_PATH = "http://localhost:8080/api/simulations";
    private static final Long ID = 1L;
    private Optional<Simulation> simulation;
    private SimulationResult simulationResult;

    @BeforeEach
    void setUp() {
        setUpSimulationResult();
        setUpSimulation();
    }

    @Test
    public void testOne() throws Exception {
        given(loadSimulationPort.loadById(ID)).willReturn(this.simulation);
        final ResultActions result = mockMvc.perform(get(BASE_PATH + "/" + ID ));
        result.andExpect(status().isOk());
        verifyJson(result, "");
    }

    @Test
    public void testNew() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        GenerateSimulationCommand generateSimulationCommand = new GenerateSimulationCommand(this.simulation.get());

        given(generateSimulationUseCase.generateAndStore(generateSimulationCommand)).willReturn(this.simulation.get());
        final ResultActions result = mockMvc
                .perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(simulation.get())));

        verify(generateSimulationUseCase, times(1)).generateAndStore(generateSimulationCommand);
        result.andExpect(status().isCreated());
        verifyJson(result, "");
    }

    @Test
    public void testUpdate() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        GenerateSimulationCommand generateSimulationCommand = new GenerateSimulationCommand(this.simulation.get());

        given(modifySimulationUseCase.modifyStoredSimulation(ID, generateSimulationCommand)).willReturn(this.simulation);
        final ResultActions result = mockMvc
                .perform(put(BASE_PATH + "/" + ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(simulation.get())));

        verify(modifySimulationUseCase, times(1)).modifyStoredSimulation(ID, generateSimulationCommand);
        result.andExpect(status().isOk());
        verifyJson(result, "");
    }

    @Test
    public void testAll() throws Exception {
        given(loadSimulationPort.loadAll()).willReturn(Collections.singletonList(this.simulation.get()));
        final ResultActions result = mockMvc.perform(get(BASE_PATH));
        result.andExpect(status().isOk());
        verifyJson(result, "[0].");
    }

    @Test
    public void testDelete() throws Exception {
        given(removeSimulationQuery.removeById(ID)).willReturn(true);

        mockMvc.perform(delete(BASE_PATH + "/" + ID ))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldThrowNotFoundOnDeleteWhenNotExist() throws Exception {
        given(removeSimulationQuery.removeById(ID)).willReturn(false);

        mockMvc.perform(delete(BASE_PATH + "/" + ID ))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message",
                        containsString("Simulation could not be found with id:") ));
    }

    @Test
    public void shouldThrowNotFoundException() throws Exception {
        Long invalidId = -1L;
        mockMvc.perform(get(BASE_PATH + "/" + invalidId ))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message",
                        containsString("Simulation could not be found with id:") ));

    }

    private void verifyJson(final ResultActions action, String expressionPrefix) throws Exception {
        Simulation simulation = this.simulation.get();
        action
                .andExpect(jsonPath( expressionPrefix + "id", is(simulation.getId().intValue()) ))
                .andExpect(jsonPath( expressionPrefix + "n", is(simulation.getN()) ))
                .andExpect(jsonPath( expressionPrefix + "p", is(((int) simulation.getP())) ))
                .andExpect(jsonPath( expressionPrefix + "i", is((int) simulation.getI()) ))
                .andExpect(jsonPath( expressionPrefix + "r", is(simulation.getR()) ))
                .andExpect(jsonPath( expressionPrefix + "m", is(simulation.getM()) ))
                .andExpect(jsonPath( expressionPrefix + "ti", is(simulation.getTi()) ))
                .andExpect(jsonPath( expressionPrefix + "tm", is(simulation.getTm()) ))
                .andExpect(jsonPath( expressionPrefix + "ts", is(simulation.getTs()) ))
                .andExpect(jsonPath( expressionPrefix + "results[0].id", is(simulationResult.getId().intValue()) ))
                .andExpect(jsonPath( expressionPrefix + "results[0].pi", is((int) simulationResult.getPi()) ))
                .andExpect(jsonPath( expressionPrefix + "results[0].pv", is((int) simulationResult.getPv()) ))
                .andExpect(jsonPath( expressionPrefix + "results[0].pm", is((int) simulationResult.getPm()) ))
                .andExpect(jsonPath( expressionPrefix + "results[0].pr", is((int) simulationResult.getPr()) ))
                .andDo(print());
    }

    private void setUpSimulationResult() {
        SimulationResult result = new SimulationResult();
        result.setId(ID);
        result.setPi(10);
        result.setPm(9);
        result.setPr(8);
        result.setPv(7);
        this.simulationResult = result;
    }

    private void setUpSimulation() {
        Simulation simulation = new Simulation();
        simulation.setId(ID);
        simulation.setN("sample");
        simulation.setP(10);
        simulation.setI(5);
        simulation.setR(0.5);
        simulation.setM(0.1);
        simulation.setTi(5);
        simulation.setTm(5);
        simulation.setTs(30);
        simulation.setResults(Collections.singletonList(this.simulationResult));
        this.simulation = Optional.of(simulation);
    }
}