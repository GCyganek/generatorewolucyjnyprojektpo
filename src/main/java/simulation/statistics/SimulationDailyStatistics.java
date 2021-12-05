package simulation.statistics;

import map.SteppeMapWithJungle;
import map.mapElements.Animal;
import map.mapElements.util.Vector2D;
import visualisation.mapStatus.MapStatusView;

import java.util.*;

public class SimulationDailyStatistics {
    private final double[] genotypeStatistics = new double[Animal.NUMBER_OF_TYPES_OF_GENES];
    private final SteppeMapWithJungle map;
    private final SimulationStatistics simulationStatistics;
    private long simulationDay = 0;
    private long animalCount = 0;
    private long plantCount = 0;
    private double averageAnimalEnergyForLivingAnimals = 0;
    private double averageLifeSpanForDeadAnimals = 0;
    private double averageChildCountForLivingAnimals = 0;
    private long deadAnimals = 0;
    private long daysLivedByDeadAnimals = 0;
    private MapStatusView mapStatusView;

    public SimulationDailyStatistics(SteppeMapWithJungle map) {
        this.map = map;
        this.simulationStatistics = new SimulationStatistics();
        map.addObserver(simulationStatistics);

        for (Animal animal : map.getAnimalLinkedList()) {
            simulationStatistics.animalPlaced(animal);
        }
    }

    public void update(long day) {
        simulationDay = day;
        plantCount = map.getPlantCount();
        animalCount = map.getAnimalCount();

        double sumAnimalEnergy = allAnimalsEnergySum();
        averageAnimalEnergyForLivingAnimals = animalCount > 0 ? sumAnimalEnergy / animalCount : 0;

        double sumAnimalChildren = allAnimalsChildrenSum();
        averageChildCountForLivingAnimals = animalCount > 0 ? sumAnimalChildren / animalCount : 0;

        int[] sumGeneTypes = allAnimalsGeneTypesSum();
        for (int i = 0; i < Animal.NUMBER_OF_TYPES_OF_GENES; i++) {
            genotypeStatistics[i] = (double) sumGeneTypes[i] / (animalCount > 0 ? animalCount : 1);
        }

        roundDoubleValues();

        mapStatusView.update();
        updateSimulationStatistics();
    }

    private void updateSimulationStatistics() {
        simulationStatistics.addToAnimalCount(animalCount);
        simulationStatistics.addToPlantCount(plantCount);
        simulationStatistics.addToAnimalEnergyForLivingAnimalsSum(averageAnimalEnergyForLivingAnimals);
        simulationStatistics.addToLifeSpanSum(averageLifeSpanForDeadAnimals);
        simulationStatistics.addToAverageChildrenForLivingAnimalsSum(averageChildCountForLivingAnimals);
    }

    public void updateLifeSpan(Animal animal) {
        deadAnimals += 1;
        daysLivedByDeadAnimals += animal.getLifeDuration();
        averageLifeSpanForDeadAnimals = (double) daysLivedByDeadAnimals / deadAnimals;
    }

    private int allAnimalsEnergySum() {
        int result = 0;
        for (Animal animal : map.getAnimalLinkedList()) {
            result += animal.getCurrentEnergy();
        }
        return result;
    }

    private int allAnimalsChildrenSum() {
        int result = 0;
        for (Animal animal : map.getAnimalLinkedList()) {
            result += animal.getChildren().size();
        }
        return result;
    }

    private int[] allAnimalsGeneTypesSum() {
        int[] result = new int[Animal.NUMBER_OF_TYPES_OF_GENES];
        for (Animal animal : map.getAnimalLinkedList()) {
            int[] genes = animal.getAnimalGenes().getGenome();
            for (int i = 0; i < Animal.NUMBER_OF_GENES; i++) {
                result[genes[i]] += 1;
            }
        }
        return result;
    }

    public int[] getDominantGenome() {
        HashMap<int[], Integer> animalsGenomes = map.getAnimalsGenomes();

        Map.Entry<int[], Integer> maxEntry = null;
        for (Map.Entry<int[], Integer> entry : animalsGenomes.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }

        if (maxEntry == null) {
            throw new NullPointerException();
        } else {
            return maxEntry.getKey();
        }
    }

    public HashSet<Vector2D> getPositionsWithDominantGenome() {
        HashSet<Vector2D> positionsWithDominantGenome = new HashSet<>();

        try {
            int[] dominantGenome = getDominantGenome();
            LinkedList<Animal> animals = map.getAnimalLinkedList();

            for (Animal animal : animals) {
                if (Arrays.equals(dominantGenome, animal.getAnimalGenes().getGenomeStatistics())) {
                    System.out.println(Arrays.toString(animal.getAnimalGenes().getGenomeStatistics()));
                    positionsWithDominantGenome.add(animal.getPosition());
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return positionsWithDominantGenome;
    }

    private void roundDoubleValues() {
        averageAnimalEnergyForLivingAnimals = (double) Math.round(averageAnimalEnergyForLivingAnimals * 1000d) / 1000d;
        averageLifeSpanForDeadAnimals = (double) Math.round(averageLifeSpanForDeadAnimals * 1000d) / 1000d;
        averageChildCountForLivingAnimals = (double) Math.round(averageChildCountForLivingAnimals * 1000d) / 1000d;

        for (int i = 0; i < Animal.NUMBER_OF_TYPES_OF_GENES; i++) {
            genotypeStatistics[i] = (double) Math.round(genotypeStatistics[i] * 1000d) / 1000d;
        }
    }

    public void saveAverageStatisticsToFile(long simulationDay, int simulationId) {
        simulationStatistics.saveAverageStatisticsToFile(simulationDay, simulationId);
    }

    public void setView(MapStatusView mapView) {
        this.mapStatusView = mapView;
        mapStatusView.initialize(this.map);
    }

    public String getSimulationDay() {
        return "Simulation Day: \n" + simulationDay;
    }

    public String getAnimalCount() {
        return "Animal count: \n" + animalCount;
    }

    public String getPlantCount() {
        return "Plant count: \n" + plantCount;
    }

    public String getAverageAnimalEnergyForLivingAnimals() {
        return "Average energy for living animals: \n" + averageAnimalEnergyForLivingAnimals;
    }

    public String getAverageLifeSpanForDeadAnimals() {
        return "Life span: \n" + averageLifeSpanForDeadAnimals;
    }

    public String getAverageChildCountForLivingAnimals() {
        return "Average children count for living animals: \n" + averageChildCountForLivingAnimals;
    }

    public String getGenotypeStatisticsValue() {
        StringBuilder array = new StringBuilder();
        for (int i = 0; i < Animal.NUMBER_OF_TYPES_OF_GENES; i++) {
            array.append("'").append(i).append("' : ").append(genotypeStatistics[i]).append("\n");
        }
        return array.toString();
    }
}
