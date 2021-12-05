package map.mapElements.util;

import util.Random;

import java.util.Arrays;
import java.util.LinkedList;

public class Genes {
    private final int[] genes;
    private final int numberOfGenes;
    private final int numberOfTypesOfGenes;
    private final int[] genomeStatistics;

    public Genes(int numberOfGenes, int numberOfTypesOfGenes) {
        this.numberOfGenes = numberOfGenes;
        this.numberOfTypesOfGenes = numberOfTypesOfGenes;
        this.genes = new int[numberOfGenes];
        this.genomeStatistics = new int[numberOfTypesOfGenes];

        fillGenesRandomly();
        checkGenesAndRepairIfNeeded();
        setGenomeStatistics();

        Arrays.sort(genes);
    }

    public Genes(Genes genes1, Genes genes2) {
        this(genes1.getNumberOfGenes(), genes1.getNumberOfTypesOfGenes());
        int firstDividePoint = Random.randomNumber(0, numberOfGenes - 2);
        int secondDividePoint = Random.randomNumber(0, numberOfGenes - 2);

        while (secondDividePoint == firstDividePoint) {
            secondDividePoint = Random.randomNumber(0, numberOfGenes - 2);
        }

        int i = 0;
        while (i <= secondDividePoint) {
            genes[i] = genes1.returnGeneAtGivenIndex(i);
            i += 1;
        }

        while (i < numberOfGenes) {
            genes[i] = genes2.returnGeneAtGivenIndex(i);
            i += 1;
        }

        checkGenesAndRepairIfNeeded();
        Arrays.sort(genes);
    }

    private void fillGenesRandomly() {
        for (int i = 0; i < numberOfGenes; i++) {
            genes[i] = Random.randomNumber(0, numberOfTypesOfGenes - 1);
        }
    }

    private void checkGenesAndRepairIfNeeded() {
        int[] numbersOfGivenGeneType = new int[numberOfTypesOfGenes];
        boolean correct = false;

        while (!correct) {
            correct = true;
            LinkedList<Integer> candidatesForChange = new LinkedList<>();

            for (int i = 0; i < numberOfTypesOfGenes; i++) {
                numbersOfGivenGeneType[i] = 0;
            }

            for (int i = 0; i < numberOfGenes; i++) {
                numbersOfGivenGeneType[genes[i]] += 1;
            }

            int missingGene = -1;
            for (int i = 0; i < numberOfTypesOfGenes; i++) {
                if (numbersOfGivenGeneType[i] > 1) {
                    candidatesForChange.add(i);
                }
                if (numbersOfGivenGeneType[i] == 0) {
                    correct = false;
                    missingGene = i;
                }
            }

            if (!correct) {
                int geneToChange = candidatesForChange.get(Random.randomNumber(0, candidatesForChange.size() - 1));

                int i = 0;
                while (genes[i] != geneToChange) {
                    i += 1;
                }

                genes[i] = missingGene;
            }
        }
    }

    @Override
    public String toString() {
        return "Genes{" +
                "genes=" + Arrays.toString(genes) +
                ", numberOfGenes=" + numberOfGenes +
                ", numberOfTypesOfGenes=" + numberOfTypesOfGenes +
                '}';
    }

    private void setGenomeStatistics() {
        for (int i = 0; i < numberOfGenes; i++) {
            genomeStatistics[genes[i]] += 1;
        }
    }

    public int[] getGenomeStatistics() {
        return genomeStatistics;
    }

    public int getNumberOfTypesOfGenes() {
        return numberOfTypesOfGenes;
    }

    public int getNumberOfGenes() {
        return numberOfGenes;
    }

    public int[] getGenome() {
        return genes;
    }

    public int returnRandomGene() {
        return genes[Random.randomNumber(0, numberOfGenes - 1)];
    }

    public int returnGeneAtGivenIndex(int index) {
        return genes[index];
    }
}
