package Simulation;

import FileService.FileManagement;
import FileService.SimulationsStatisticsFilePath;
import Interfaces.Observers.IAnimalPlacedObserver;
import MapElementsClasses.Animal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SimulationStatistics implements IAnimalPlacedObserver {
    private final Map<int[], Integer> allAnimalGenomes = new HashMap<>();
    private long animalCount = 0;
    private long plantCount = 0;
    private double animalEnergyForLivingAnimalsSum = 0;
    private double lifeSpanSum = 0;
    private double averageChildrenForLivingAnimalsSum = 0;

    public void addToAnimalCount(long animals) {
        animalCount += animals;
    }

    public void addToPlantCount(long plants) {
        plantCount += plants;
    }

    public void addToAnimalEnergyForLivingAnimalsSum(double animalEnergy) {
        animalEnergyForLivingAnimalsSum += animalEnergy;
    }

    public void addToLifeSpanSum(double lifeSpan) {
        lifeSpanSum += lifeSpan;
    }

    public void addToAverageChildrenForLivingAnimalsSum(double averageChildren) {
        averageChildrenForLivingAnimalsSum += averageChildren;
    }

    @Override
    public void animalPlaced(Animal animal) {
        int[] genome = animal.getAnimalGenes().getGenome();

        if (allAnimalGenomes.containsKey(genome)) {
            int value = allAnimalGenomes.get(genome);
            allAnimalGenomes.remove(genome);
            allAnimalGenomes.put(genome, value + 1);
        } else {
            allAnimalGenomes.put(genome, 1);
        }

    }

    private Map.Entry<int[], Integer> getGenomeWithTheMostOccurrences() {

        Map.Entry<int[], Integer> maxEntry = null;
        for (Map.Entry<int[], Integer> entry : allAnimalGenomes.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }

        if (maxEntry == null) {
            throw new NullPointerException();
        } else {
            return maxEntry;
        }
    }

    public void saveAverageStatisticsToFile(long simulationDay, int simulationId) {
        Map.Entry<int[], Integer> dominantGenome = getGenomeWithTheMostOccurrences();
        StringBuilder text = new StringBuilder("===========");

        text.append("\nAverage statistics for simulation number ").append(simulationId).append(" after ")
                .append(simulationDay).append(" days:");

        text.append("\n   Average animal count of all days: ")
                .append(roundDoubleValues((double) animalCount / simulationDay));

        text.append("\n   Average plant count of all days: ")
                .append(roundDoubleValues((double) plantCount / simulationDay));

        text.append("\n   Average life energy for living animals of all days: ")
                .append(roundDoubleValues(animalEnergyForLivingAnimalsSum / simulationDay));

        text.append("\n   Average life span for dead animals of all days: ")
                .append(roundDoubleValues(lifeSpanSum / simulationDay));

        text.append("\n   Average children count for living animals of all days: ")
                .append(roundDoubleValues(averageChildrenForLivingAnimalsSum / simulationDay));

        text.append("\n   Genome with the most occurrences of all days: \n   ")
                .append(Arrays.toString(dominantGenome.getKey())).append("\n   with ").append(dominantGenome.getValue())
                .append(" occurrences\n\n");

        String textToFile = text.toString();
        FileManagement.appendToFile(SimulationsStatisticsFilePath.path, textToFile);
    }

    private double roundDoubleValues(double value) {
        return (double) Math.round(value * 1000d) / 1000d;
    }

}
