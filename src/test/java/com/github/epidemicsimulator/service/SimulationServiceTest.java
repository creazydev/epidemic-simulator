package com.github.epidemicsimulator.service;

import com.github.epidemicsimulator.domain.Simulation;
import com.github.epidemicsimulator.domain.SimulationResult;
import com.github.epidemicsimulator.repository.SimulationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimulationServiceTest {

    @Mock
    private SimulationRepository repository;

    @Mock
    private Generator<Simulation, SimulationResult> generator;

    private SimulationService service;
    private final Long ID = 1L;

    @BeforeEach
    void setUp() {
        service = new SimulationService(generator, repository);
    }

    @AfterEach
    void tearDown() {
        clearInvocations(repository);
    }

    @Test
    void testGetOne() {
        service.getOne(ID);
        verify(repository, times(1)).findById(ID);
    }

    @Test
    void testGetAll() {
        service.getAll();
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGenerateAndSave() {
        Simulation simulation = new Simulation();
        service.generateAndSave(simulation);
        verify(generator, times(1)).generate(simulation);
        verify(repository, times(1)).save(simulation);
    }

    @Test
    void testDelete() {
        service.deleteSimulation(ID);
        verify(repository, times(1)).deleteById(ID);
    }
}