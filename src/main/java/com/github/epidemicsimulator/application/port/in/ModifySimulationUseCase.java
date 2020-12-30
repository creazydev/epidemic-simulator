package com.github.epidemicsimulator.application.port.in;

import com.github.epidemicsimulator.domain.Simulation;

import java.util.Optional;

public interface ModifySimulationUseCase {
    Optional<Simulation> modifyStoredSimulation(Long id, GenerateSimulationCommand validatedSimulation);
}
