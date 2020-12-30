package com.github.epidemicsimulator.application.port.in;

import com.github.epidemicsimulator.domain.Simulation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Getter
@ToString
@EqualsAndHashCode
public class GenerateSimulationCommand {
    @Min(value = 1, message = "id must be greater than 0.")
    private final Long id;

    @Length(min = 1, max = 64, message = "n must have at least 1 char")
    private final String n;

    @Min(value = 1, message = "p must be greater than 1.")
    private final long p;

    @Min(value = 1, message = "i must be greater than 1.")
    private final long i;

    @Positive(message = "r must be greater than 0.")
    private final double r;

    @DecimalMax(value = "1.00", message = "m must be less than 1.")
    @Positive(message = "m must be greater than 0.")
    private final double m;

    @Min(value = 1, message = "ti must be greater than 1.")
    private final int ti;

    @Min(value = 1, message = "tm must be greater than 1.")
    private final int tm;

    @Min(value = 1, message = "ts must be greater than 1.")
    private final int ts;

    public GenerateSimulationCommand(Simulation simulation) {
        this.id = simulation.getId();
        this.n = simulation.getN();
        this.p = simulation.getP();
        this.i = simulation.getI();
        this.r = simulation.getR();
        this.m = simulation.getM();
        this.ti = simulation.getTi();
        this.tm = simulation.getTm();
        this.ts = simulation.getTs();
    }


}
