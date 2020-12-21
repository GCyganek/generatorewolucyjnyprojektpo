package Simulations;

import InputData.Configurations;
import InputData.GetJsonData;
import Simulation.Simulation;

import java.util.ArrayList;

public class SimulationsContainer {
    private static final ArrayList<Simulation> simulations = new ArrayList<>();

    public static void createSimulations() {
        Configurations config = GetJsonData.getConfig();

        for (int i = 0; i < config.getSimulations(); i++) {
            Simulation simulation = new Simulation(i + 1);
            simulations.add(simulation);
        }
    }

    public static void runSimulation() {
        for (Simulation simulation : simulations) {
            if (simulation.getStatus()) {
                simulation.simulateOneDay();
            }
        }
    }

    public static ArrayList<Simulation> getSimulations() {
        return simulations;
    }
}
