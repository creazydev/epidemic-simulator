package com.github.epidemicsimulator.application;

import com.github.epidemicsimulator.application.port.in.GenerateSimulationCommand;
import com.github.epidemicsimulator.domain.SimulationResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BasicSimulationGenerator implements Generator<GenerateSimulationCommand, SimulationResult> {

    @Override
    public List<SimulationResult> generate(GenerateSimulationCommand simulation) {
        InnerGenerator innerGenerator = new InnerGenerator();
        return innerGenerator.generate(simulation);
    }

    private static class DayCounter {
        int ts, tm, ti;

        protected DayCounter(int initial) {
            ts = tm = ti = initial;
        }

        public void increment() {
            ts++;
            tm++;
            ti++;
        }

        public void resetTm() {
            tm = 0;
        }

        public void resetTi() {
            ti = 0;
        }
    }

    private static class InnerGenerator implements Generator<GenerateSimulationCommand, SimulationResult> {
        private DayCounter counter;
        private SimulationResult result;
        private long previousPi, newDeaths, newRecovers, newInfections;

        @Override
        public List<SimulationResult> generate(GenerateSimulationCommand simulation) {
            List<SimulationResult> results = new ArrayList<>(simulation.getTs());
            this.result = getInitialResult(simulation);
            results.add(0, SimulationResult.copy(this.result));

            for (counter = new DayCounter(1); counter.ts < simulation.getTs(); counter.increment()) {
                handleDailyInfection(simulation.getR());

                if (hasInfectionToDeathTimeElapsed(simulation.getTm())) {
                    previousPi = results.get(counter.ts - simulation.getTm()).getPi();
                    newDeaths = round(
                            ((result.getPi() < previousPi) ? result.getPi() : previousPi) * simulation.getM());

                    result.setPi(result.getPi() - newDeaths);
                    result.setPm(result.getPm() + newDeaths);
                    counter.resetTm();
                }

                if (hasInfectionToRecoveryTimeElapsed(simulation.getTi())) {
                    previousPi = results.get(counter.ts - simulation.getTi()).getPi();
                    newRecovers = round(
                            ((result.getPi() < previousPi) ? result.getPi() : previousPi) * (1 - simulation.getM()));
                    result.setPi(result.getPi() - newRecovers);
                    result.setPr(result.getPr() + newRecovers);
                    counter.resetTi();
                }

                results.add(SimulationResult.copy(result));
            }
            return results;
        }

        private void handleDailyInfection(double r) {
            if (isPositive(result.getPv())) {
                newInfections = round(result.getPi() * r);

                if (hasAnyHealthyLeft()) {
                    result.setPi(result.getPi() + newInfections);
                    result.setPv(result.getPv() - newInfections);
                } else {
                    result.setPi(result.getPi() + result.getPv());
                    result.setPv(0L);
                }
            }
        }

        private boolean hasAnyHealthyLeft() {
            return result.getPv() > newInfections;
        }

        private boolean isPositive(long arg) {
            return arg > 0;
        }

        private boolean hasInfectionToDeathTimeElapsed(int tm) {
            return counter.tm == tm && counter.ts >= tm;
        }

        private boolean hasInfectionToRecoveryTimeElapsed(int ti) {
            return counter.ti == ti && counter.ts >= ti;
        }

        private long round(double arg) {
            return Math.round(arg);
        }

    }

    private static SimulationResult getInitialResult(GenerateSimulationCommand simulation) {
        return new SimulationResult(
                null,
                simulation.getI(),
                simulation.getP() - simulation.getI(),
                0L,
                0L);
    }
}
