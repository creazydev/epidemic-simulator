package com.github.epidemicsimulator.adapter.out.persistence.mapper;

import com.github.epidemicsimulator.adapter.out.persistence.entity.SimulationResultJpaEntity;
import com.github.epidemicsimulator.domain.SimulationResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SimulationResultMapper implements EntityMapper<SimulationResult, SimulationResultJpaEntity> {
    @Override
    public SimulationResultJpaEntity toEntity(SimulationResult domainModel) {
        return new SimulationResultJpaEntity(
                domainModel.getId(),
                domainModel.getPi(),
                domainModel.getPv(),
                domainModel.getPm(),
                domainModel.getPr(),
                null
        );
    }

    @Override
    public List<SimulationResultJpaEntity> toEntityList(List<SimulationResult> domainModels) {
        if (domainModels != null) {
            return domainModels.stream().map(this::toEntity).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @Override
    public SimulationResult toDomainModel(SimulationResultJpaEntity entity) {
        return new SimulationResult(
                entity.getId(),
                entity.getPi(),
                entity.getPv(),
                entity.getPm(),
                entity.getPr()
        );
    }

    @Override
    public List<SimulationResult> toDomainModelList(List<SimulationResultJpaEntity> entities) {
        if (entities != null) {
            return entities.stream().map(this::toDomainModel).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
