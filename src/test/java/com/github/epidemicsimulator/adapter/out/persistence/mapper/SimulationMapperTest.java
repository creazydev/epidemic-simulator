package com.github.epidemicsimulator.adapter.out.persistence.mapper;

import com.github.epidemicsimulator.domain.Simulation;
import com.github.epidemicsimulator.domain.SimulationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SimulationMapperTest {
    private SimulationMapper simulationMapper;
    private static final Long ID = 1L;

    @BeforeEach
    void setUp() {
        simulationMapper = new SimulationMapper(new SimulationResultMapper());
    }

    @Test
    void shouldReturnEqual() {
        Simulation expected = setUpSimulation();
        Simulation simulation = simulationMapper.toDomainModel(simulationMapper.toEntity(expected));
        assertEquals(expected, simulation);
    }

    @Test
    void shouldNotThrowExceptionOnEmptyList() {
        assertDoesNotThrow(() -> simulationMapper.toEntityList(null));
    }

    private Simulation setUpSimulation() {
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
        simulation.setResults(Collections.singletonList(setUpSimulationResult()));
        return simulation;
    }

    private SimulationResult setUpSimulationResult() {
        SimulationResult result = new SimulationResult();
        result.setId(ID);
        result.setPi(10);
        result.setPm(9);
        result.setPr(8);
        result.setPv(7);
        return result;
    }
}