package com.github.epidemicsimulator.application.service;

import com.github.epidemicsimulator.application.Generator;
import com.github.epidemicsimulator.application.port.in.GenerateSimulationCommand;
import com.github.epidemicsimulator.application.port.in.GenerateSimulationUseCase;
import com.github.epidemicsimulator.application.port.out.StoreSimulationPort;
import com.github.epidemicsimulator.domain.Simulation;
import com.github.epidemicsimulator.domain.SimulationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
class GenerateSimulationService implements GenerateSimulationUseCase {
    private final Generator<GenerateSimulationCommand, SimulationResult> generator;
    private final StoreSimulationPort storeSimulationPort;

    @Override
    @Transactional
    public Simulation generateAndStore(@Valid GenerateSimulationCommand validatedSimulation) {
        Simulation simulation = new Simulation(
                validatedSimulation.getId(),
                validatedSimulation.getN(),
                validatedSimulation.getP(),
                validatedSimulation.getI(),
                validatedSimulation.getR(),
                validatedSimulation.getM(),
                validatedSimulation.getTi(),
                validatedSimulation.getTm(),
                validatedSimulation.getTs(),
                generator.generate(validatedSimulation)
        );

        return storeSimulationPort.save(simulation);
    }
}
