package com.github.epidemicsimulator.service;

import com.github.epidemicsimulator.domain.Simulation;
import com.github.epidemicsimulator.domain.SimulationResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DailySimulationResultFactory implements SimulationResultFactory<Simulation, SimulationResult> {
    @Override
    public List<SimulationResult> create(Simulation simulation) {
        //initial simulation result vars
        long pv = simulation.getP() - simulation.getI();
        long pi = simulation.getI();
        long pm = 0;
        long pr = 0;

        //pocket variables
        long previousPi;
        long newDeaths;
        long newRecovers;
        long newInfections;

        //time unit counters (days)
        int ts, tm, ti;

        List<SimulationResult> results = new ArrayList<>(simulation.getTs());
        results.add(getInitialResult(pi, pv, simulation));

        for (ts = tm = ti = 1; ts < simulation.getTs() ; ts++, tm++, ti++) {
            //death case
            if (hasEnoughTimePassed(tm, simulation.getTm(), ts)) {
                previousPi = results.get(ts - simulation.getTm()).getPi();
                if (!hasLeftMoreThan(pi, previousPi)) {
                    previousPi = pi;
                }

                newDeaths = round(previousPi * simulation.getM());
                pi -= newDeaths;
                pm += newDeaths;
                tm = 0;
            }

            //recovery case
            if (hasEnoughTimePassed(ti, simulation.getTi(), ts)) {
                previousPi = results.get(ts - simulation.getTi()).getPi();
                if (!hasLeftMoreThan(pi, previousPi)) {
                    previousPi = pi;
                }

                newRecovers = round(previousPi * (1-simulation.getM()) );
                pi -= newRecovers;
                pr += newRecovers;
                ti = 0;
            }

            if (hasLeftMoreThan(pv, 0)) {
                newInfections = round(pi * simulation.getR());
                if (!hasLeftMoreThan(pv, newInfections)) {
                    pi += pv;
                    pv = 0;
                } else {
                    pi += newInfections;
                    pv -= newInfections;
                }
            }

            //init and add
            SimulationResult result = new SimulationResult();
                result.setPi(pi);
                result.setPv(pv);
                result.setPm(pm);
                result.setPr(pr);
                result.setSimulation(simulation);
            results.add(result);
        }
        return results;
    }

    public static long round(double arg) {
        return Math.round(arg);
    }

    private boolean hasEnoughTimePassed(int timeSinceLast, int timeRequired, int dayOfSimulation) {
        return timeSinceLast == timeRequired && dayOfSimulation >= timeRequired;
    }

    private boolean hasLeftMoreThan(long first, long second) {
        return first > second;
    }

    private SimulationResult getInitialResult(long pi, long pv, Simulation simulation) {
        SimulationResult result = new SimulationResult();
        result.setPi(pi);
        result.setPv(pv);
        result.setPm(0L);
        result.setPr(0L);
        result.setSimulation(simulation);
        return result;
    }
}
