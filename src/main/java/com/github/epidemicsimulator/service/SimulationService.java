package com.github.epidemicsimulator.service;

import com.github.epidemicsimulator.domain.SimulationResult;
import com.github.epidemicsimulator.domain.Simulation;
import com.github.epidemicsimulator.repository.SimulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimulationService {
    private final Generator<Simulation, SimulationResult> resultFactory;
    private final SimulationRepository repository;

    public List<Simulation> getAll() {
        return repository.findAll();
    }

    @Transactional
    public Simulation generateAndSave(Simulation simulation) {
        simulation.setResults(resultFactory.generate(simulation));
        return repository.save(simulation);
    }

    public Optional<Simulation> getOne(Long id) {
        return repository.findById(id);
    }

    public void deleteSimulation(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }
}
