package com.github.epidemicsimulator.adapter.out.persistence;

import com.github.epidemicsimulator.adapter.out.persistence.mapper.SimulationMapper;
import com.github.epidemicsimulator.adapter.out.persistence.mapper.SimulationResultMapper;
import com.github.epidemicsimulator.domain.Simulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SimulationPersistenceAdapterTest {
    @Autowired
    private SimulationRepository repository;

    private SimulationPersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new SimulationPersistenceAdapter(repository, new SimulationMapper(new SimulationResultMapper()));
    }

    @Test
    void injectedJpaRepositoryIsNotNull() {
        assertThat(repository).isNotNull();
    }

    @Test
    void shouldReturnEqualObjectOnSave() {
        Simulation expected = setUpSimulation();
        Simulation saved = adapter.save(expected);
        expected.setId(saved.getId());
        assertEquals(expected, adapter.save(expected));
    }

    @Test
    void shouldReturnFalseWhenNotExist() {
        Simulation expected = setUpSimulation();
        assertFalse(adapter.removeById(expected.getId()));
    }

    @Test
    void shouldReturnTrueWhenDeleted() {
        Simulation saved = adapter.save(setUpSimulation());
        assertTrue(adapter.removeById(saved.getId()));
    }

    @Test
    void shouldFindObjectAfterSave() {
        Simulation savedSimulation = adapter.save(setUpSimulation());
        Simulation foundSimulation = adapter.loadById(savedSimulation.getId()).get();
        assertEquals(savedSimulation, foundSimulation);
    }

    private static Simulation setUpSimulation() {
        Simulation simulation = new Simulation();
        simulation.setP(10);
        simulation.setI(5);
        simulation.setR(0.5);
        simulation.setM(0.1);
        simulation.setTi(5);
        simulation.setTm(5);
        simulation.setTs(30);
        return simulation;
    }
}