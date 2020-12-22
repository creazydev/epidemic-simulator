package com.github.epidemicsimulator.service;

import com.github.epidemicsimulator.domain.Simulation;
import com.github.epidemicsimulator.domain.SimulationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultGeneratorWrapperTest {
    private ResultGeneratorWrapper generator;
    private Simulation simulation;
    private final int TS = 10;

    @BeforeEach
    void setUp() {
        generator = new ResultGeneratorWrapper();
        setUpSimulation();
    }

    @Test
    void shouldGenerateAsManyObjectsAsTiNumber() {
        assertEquals(simulation.getTs(), generator.generate(simulation).size());
    }

    @Test
    void shouldSimulationPopulationEqualsResultPopulation() {
        for (SimulationResult result: generator.generate(simulation)) {
            long resultPopulation = result.getPi() + result.getPm() + result.getPr() + result.getPv();
            assertEquals(simulation.getP(), resultPopulation);
        }
    }

    @Test
    void shouldNotSetValueLessThan0() {
        for (SimulationResult result: generator.generate(simulation)) {
            assertTrue(
                    result.getPi() >= 0 && result.getPm() >= 0 && result.getPr() >= 0 && result.getPv() >= 0 );
        }
    }

    private void setUpSimulation() {
        Simulation simulation = new Simulation();
        simulation.setN("sample");
        simulation.setP(1000);
        simulation.setI(5);
        simulation.setR(0.7);
        simulation.setM(0.2);
        simulation.setTi(3);
        simulation.setTm(5);
        simulation.setTs(this.TS);
        this.simulation = simulation;
    }
}