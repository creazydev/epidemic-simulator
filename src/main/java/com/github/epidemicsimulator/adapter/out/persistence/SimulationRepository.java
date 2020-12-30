package com.github.epidemicsimulator.adapter.out.persistence;

import com.github.epidemicsimulator.adapter.out.persistence.entity.SimulationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SimulationRepository extends JpaRepository<SimulationJpaEntity, Long> {
}
