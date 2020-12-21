package com.github.epidemicsimulator.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "simulations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "results"})
@ToString
public class Simulation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "n")
    @Length(min = 1, max = 64, message = "n must have at least 1 char")
    private String n;

    @Column(name = "p")
    @Min(value = 1, message = "p must be greater than 1.")
    private long p;

    @Column(name = "i")
    @Min(value = 1, message = "i must be greater than 1.")
    private long i;

    @Column(name = "r")
    @Positive(message = "r must be greater than 0.")
    private double r;

    @Column(name = "m")
    @DecimalMax(value = "1.00", message = "m must be less than 1.")
    @Positive(message = "m must be greater than 0.")
    private double m;

    @Column(name = "ti")
    @Min(value = 1, message = "ti must be greater than 1.")
    private int ti;

    @Column(name = "tm")
    @Min(value = 1, message = "tm must be greater than 1.")
    private int tm;

    @Column(name = "ts")
    @Min(value = 1, message = "ts must be greater than 1.")
    private int ts;

    @OneToMany(mappedBy = "simulation", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<SimulationResult> results;
}


