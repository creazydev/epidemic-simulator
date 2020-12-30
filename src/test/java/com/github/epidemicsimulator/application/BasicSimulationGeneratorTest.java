package com.github.epidemicsimulator.application;

import com.github.epidemicsimulator.application.port.in.GenerateSimulationCommand;
import com.github.epidemicsimulator.domain.Simulation;
import com.github.epidemicsimulator.domain.SimulationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BasicSimulationGeneratorTest {
    private BasicSimulationGenerator generator;
    private GenerateSimulationCommand command;
    private final int TS = 10;

    @BeforeEach
    void setUp() {
        generator = new BasicSimulationGenerator();
        command = new GenerateSimulationCommand(setUpSimulation());
    }

    @Test
    void shouldGenerateAsManyObjectsAsTiNumber() {
        assertEquals(command.getTs(), generator.generate(command).size());
    }

    @Test
    void shouldSimulationPopulationEqualsResultPopulation() {
        for (SimulationResult result: generator.generate(command)) {
            long resultPopulation = result.getPi() + result.getPm() + result.getPr() + result.getPv();
            assertEquals(command.getP(), resultPopulation);
        }
    }

    @Test
    void shouldNotSetValueLessThan0() {
        for (SimulationResult result: generator.generate(command)) {
            assertTrue(
                    result.getPi() >= 0 && result.getPm() >= 0 && result.getPr() >= 0 && result.getPv() >= 0 );
        }
    }

    private Simulation setUpSimulation() {
        Simulation simulation = new Simulation();
        simulation.setN("sample");
        simulation.setP(1000);
        simulation.setI(5);
        simulation.setR(0.7);
        simulation.setM(0.2);
        simulation.setTi(3);
        simulation.setTm(5);
        simulation.setTs(this.TS);
        return simulation;
    }

}