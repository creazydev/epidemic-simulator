package com.github.epidemicsimulator.repository;

import com.github.epidemicsimulator.domain.Simulation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SimulationRepositoryTest {
    @Autowired
    private SimulationRepository repository;

    @Test
    void injectedJpaRepositoryIsNotNull() {
        assertThat(repository).isNotNull();
    }

    @Test
    void shouldReturnEqualObjectOnSave() {
        Simulation expected = setUpSimulation();
        assertEquals(expected, repository.save(expected));
    }

    @Test
    void shouldFindObjectAfterSave() {
        Simulation savedSimulation = repository.save(setUpSimulation());
        Simulation foundSimulation = repository.findById(savedSimulation.getId()).get();
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