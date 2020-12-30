package com.github.epidemicsimulator.application.port.out;

import com.github.epidemicsimulator.domain.Simulation;

import java.util.List;
import java.util.Optional;

public interface LoadSimulationPort {
    Optional<Simulation> loadById(Long id);
    List<Simulation> loadAll();
}
