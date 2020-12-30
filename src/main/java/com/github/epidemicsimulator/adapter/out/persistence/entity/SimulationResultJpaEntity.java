package com.github.epidemicsimulator.adapter.out.persistence.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "simulation_results")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(exclude = "simulation")
@ToString(exclude = "simulation")
public class SimulationResultJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "pi")
    private long pi;

    @Column(name = "pv")
    private long pv;

    @Column(name = "pm")
    private long pm;

    @Column(name = "pr")
    private long pr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "simulation_id", referencedColumnName = "id", nullable = false)
    private SimulationJpaEntity simulation;
}
