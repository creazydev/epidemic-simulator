package com.github.epidemicsimulator.service;

import com.github.epidemicsimulator.domain.Simulation;
import com.github.epidemicsimulator.domain.SimulationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DailySimulationResultFactoryTest {

    private DailySimulationResultFactory factory;
    private Simulation simulation;
    private final int TS = 10;

    @BeforeEach
    void setUp() {
        factory = new DailySimulationResultFactory();
        setUpSimulation();
    }

    @Test
    void shouldGenerateAsManyObjectsAsTiNumber() {
        assertEquals(simulation.getTs(), factory.create(simulation).size());
    }

    @Test
    void shouldSimulationPopulationEqualsResultPopulation() {
        for (SimulationResult result: factory.create(simulation)) {
            long resultPopulation = result.getPi() + result.getPm() + result.getPr() + result.getPv();
            assertEquals(simulation.getP(), resultPopulation);
        }
    }

    @Test
    void shouldNotSetValueLessThan0() {
        for (SimulationResult result: factory.create(simulation)) {
            assertTrue(
                    result.getPi() >= 0 && result.getPm() >= 0 && result.getPi() >= 0 && result.getPv() >= 0 );
        }
    }

    @Test
    void testRound() {
        long expected = 11;
        double given = 10.5;
        assertEquals(expected, DailySimulationResultFactory.round(given));
    }

    private void setUpSimulation() {
        Simulation simulation = new Simulation();
        simulation.setN("sample");
        simulation.setP(10);
        simulation.setI(5);
        simulation.setR(0.5);
        simulation.setM(0.1);
        simulation.setTi(3);
        simulation.setTm(5);
        simulation.setTs(this.TS);
        this.simulation = simulation;
    }
}