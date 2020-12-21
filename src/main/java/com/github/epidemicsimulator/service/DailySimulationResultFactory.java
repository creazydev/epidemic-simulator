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
        List<SimulationResult> results = new ArrayList<>(simulation.getTs());

        long pv = simulation.getPopulation() - simulation.getInfected();
        long pi = simulation.getInfected();
        long pm = 0;
        long pr = 0;

        long prevPi;
        long newDeaths;
        long newRecovers;
        long newInfected;

        for (int ts = 1, tm = 1, ti = 1; ts <= simulation.getTs() ; ts++, tm++, ts++) {
            if ( tm == simulation.getTm() && ts >= simulation.getTm() ) {
                prevPi = results.get(ts - simulation.getTs()).getPi();
                newDeaths = (long) (prevPi * simulation.getM());

                pi -= newDeaths;
                pm += newDeaths;
                tm = 0;
            }

            if ( ti == simulation.getTm() && ts >= simulation.getTi() ) {
                prevPi = results.get(ts - simulation.getTs()).getPi();
                newRecovers = (long) (prevPi / simulation.getM());

                pi -= newRecovers;
                pr += newRecovers;
                ti = 0;
            }

            newInfected = (long) (pi * simulation.getR());
            pi += newInfected;
            pv -= newInfected;

            SimulationResult result = new SimulationResult();
            result.setPi(pi);
            result.setPv(pv);
            result.setPm(pm);
            result.setPr(pr);
            results.add(result);
        }

        results.forEach( (simulationResult) -> simulationResult.setSimulation(simulation) );
        return results;
    }
}
