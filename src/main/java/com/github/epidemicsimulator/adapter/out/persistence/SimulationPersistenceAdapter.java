package com.github.epidemicsimulator.adapter.out.persistence;

import com.github.epidemicsimulator.adapter.out.persistence.entity.SimulationJpaEntity;
import com.github.epidemicsimulator.adapter.out.persistence.mapper.EntityMapper;
import com.github.epidemicsimulator.application.port.out.LoadSimulationPort;
import com.github.epidemicsimulator.application.port.out.RemoveSimulationQuery;
import com.github.epidemicsimulator.application.port.out.StoreSimulationPort;
import com.github.epidemicsimulator.domain.Simulation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class SimulationPersistenceAdapter implements LoadSimulationPort, RemoveSimulationQuery, StoreSimulationPort {
    private final SimulationRepository repository;
    private final EntityMapper<Simulation, SimulationJpaEntity> mapper;

    @Override
    public Optional<Simulation> loadById(Long id) {
        return repository.findById(id).map(mapper::toDomainModel);
    }

    @Override
    public List<Simulation> loadAll() {
        return mapper.toDomainModelList(repository.findAll());
    }

    @Override
    public boolean removeById(Long id) {
        if (id != null && repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Simulation save(Simulation simulation) {
        SimulationJpaEntity entity = repository.save(mapper.toEntity(simulation));
        return mapper.toDomainModel(entity);
    }
}
