package com.github.epidemicsimulator.adapter.out.persistence.mapper;

import com.github.epidemicsimulator.adapter.out.persistence.entity.SimulationJpaEntity;
import com.github.epidemicsimulator.adapter.out.persistence.entity.SimulationResultJpaEntity;
import com.github.epidemicsimulator.domain.Simulation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SimulationMapper implements EntityMapper<Simulation, SimulationJpaEntity> {
    private final SimulationResultMapper simulationResultMapper;

    @Override
    public SimulationJpaEntity toEntity(Simulation domainModel) {
        SimulationJpaEntity simulationJpaEntity = new SimulationJpaEntity(
                domainModel.getId(),
                domainModel.getN(),
                domainModel.getP(),
                domainModel.getI(),
                domainModel.getR(),
                domainModel.getM(),
                domainModel.getTi(),
                domainModel.getTm(),
                domainModel.getTs(),
                null
        );

        List<SimulationResultJpaEntity> results = simulationResultMapper.toEntityList(domainModel.getResults());

        if (results != null) {
            results.forEach( (result) -> result.setSimulation(simulationJpaEntity) );
            simulationJpaEntity.setResults(results);
        }

        return simulationJpaEntity;
    }

    @Override
    public List<SimulationJpaEntity> toEntityList(List<Simulation> domainModels) {
        if (domainModels != null) {
            return domainModels.stream().map(this::toEntity).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @Override
    public Simulation toDomainModel(SimulationJpaEntity entity) {
        return new Simulation(
                entity.getId(),
                entity.getN(),
                entity.getP(),
                entity.getI(),
                entity.getR(),
                entity.getM(),
                entity.getTi(),
                entity.getTm(),
                entity.getTs(),
                simulationResultMapper.toDomainModelList(entity.getResults())
        );
    }

    @Override
    public List<Simulation> toDomainModelList(List<SimulationJpaEntity> entities) {
        if (entities != null) {
            return entities.stream().map(this::toDomainModel).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
