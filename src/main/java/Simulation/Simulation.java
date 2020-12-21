package Simulation;

import InputData.Configurations;
import InputData.GetJsonData;
import Interfaces.ISimulation;
import Interfaces.Observers.IAnimalDiedObserver;
import Interfaces.Observers.ITimePassedObserver;
import Interfaces.Publishers.ITimePassedPublisher;
import MapElementsClasses.Animal;
import Visualisation.MapStatus.MapStatusView;
import WorldClasses.SteppeMapWithJungle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class Simulation implements ISimulation, IAnimalDiedObserver, ITimePassedPublisher {
    private final int moveEnergy;
    private final int animalsOnStart;
    private final int id;
    private final SimulationDailyStatistics simulationDailyStatistics;
    private final SimulationOperations simulationOperations;
    private final Map<ITimePassedObserver, Long> timePassedObservers = new HashMap<>();
    private final HashSet<Long> saveToFileDays = new HashSet<>();
    private long simulationDay;
    private boolean simulationRunning = true;

    public Simulation(int id) {
        Configurations config = GetJsonData.getConfig();

        this.id = id;
        SteppeMapWithJungle map = new SteppeMapWithJungle(config.getWidth(), config.getHeight(), config.getJungleRatio());
        this.simulationOperations = new SimulationOperations(map, config.getPlantEnergy(), config.getStartEnergy());
        this.animalsOnStart = config.getAnimalsOnStart();
        this.moveEnergy = config.getMoveEnergy();
        this.simulationDay = 0;

        prepareSimulation();

        this.simulationDailyStatistics = new SimulationDailyStatistics(map);
        simulationOperations.addObserver(this);
    }

    public void prepareSimulation() {
        for (int i = 0; i < animalsOnStart; i++) {
            simulationOperations.placeAnimalOnRandomField();
        }
    }

    public void simulateOneDay() {
        simulationOperations.removeDeadAnimals(simulationDay);
        simulationOperations.runAnimals();
        simulationOperations.animalsEating();
        simulationOperations.oneDayAnimalsEnergyChange(-moveEnergy);
        simulationOperations.copulation(simulationDay);
        simulationOperations.placeGrass();
        this.simulationDay += 1;
        simulationDailyStatistics.update(simulationDay);
        checkTimePassed();
        checkSaveToFileDays();
    }

    public void animalDied(Animal deadAnimal) {
        simulationDailyStatistics.updateLifeSpan(deadAnimal);
    }

    private void checkTimePassed() {
        ArrayList<ITimePassedObserver> timePassedObserversToRemove = new ArrayList<>();

        for (Map.Entry<ITimePassedObserver, Long> entry : timePassedObservers.entrySet()) {
            if (entry.getValue() == simulationDay) {
                entry.getKey().timePassed();
                timePassedObserversToRemove.add(entry.getKey());
            }
        }

        for (ITimePassedObserver observer : timePassedObserversToRemove) {
            timePassedObservers.remove(observer);
        }
    }

    private void checkSaveToFileDays() {
        boolean saved = false;
        for (long day : saveToFileDays) {
            if (day == simulationDay) {
                simulationDailyStatistics.saveAverageStatisticsToFile(simulationDay, id);
                saved = true;
            }
        }

        if (saved) {
            saveToFileDays.remove(simulationDay);
        }

    }

    @Override
    public void addObserver(ITimePassedObserver observer, long day) {
        timePassedObservers.put(observer, day);
    }

    @Override
    public void removeObserver(ITimePassedObserver observer) {
        timePassedObservers.remove(observer);
    }

    public void addToSaveToFileDays(long day) {
        saveToFileDays.add(day);
    }

    public void setView(MapStatusView mapStatusView) {
        simulationDailyStatistics.setView(mapStatusView);
        simulationDailyStatistics.update(simulationDay);
    }

    public SimulationDailyStatistics getSimulationStatistics() {
        return simulationDailyStatistics;
    }

    public void changeSimulationRunning() {
        simulationRunning = !simulationRunning;
    }

    public boolean getStatus() {
        return simulationRunning;
    }

    public long getSimulationDay() {
        return simulationDay;
    }

    public int getId() {
        return id;
    }

}
