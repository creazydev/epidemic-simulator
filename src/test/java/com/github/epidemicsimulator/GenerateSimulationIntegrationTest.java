package com.github.epidemicsimulator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.epidemicsimulator.domain.Simulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GenerateSimulationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String BASE_PATH = "http://localhost:8080/api/simulations";
    private Simulation simulation;

    @BeforeEach
    void setUp() {
        setUpSimulation();
    }

    @Test
    void generationWorksThroughAllLayers() throws Exception {
        mockMvc.perform(post(BASE_PATH)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(this.simulation)))
                .andExpect(status().isCreated());
    }

    private void setUpSimulation() {
        Simulation simulation = new Simulation();
        simulation.setN("sample");
        simulation.setP(10);
        simulation.setI(5);
        simulation.setR(0.5);
        simulation.setM(0.1);
        simulation.setTi(5);
        simulation.setTm(5);
        simulation.setTs(30);
        this.simulation = simulation;
    }
}

