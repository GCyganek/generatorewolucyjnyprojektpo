package WorldClasses;

import Interfaces.IWorldMap;
import Interfaces.Observers.IAnimalPlacedObserver;
import Interfaces.Publishers.IAnimalPlacedPublisher;
import Interfaces.Observers.IPositionChangeObserver;
import Interfaces.Publishers.IPositionChangedPublisher;
import MapElementsClasses.Animal;
import MapElementsClasses.Grass;
import MapElementsClasses.Vector2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class SteppeMapWithJungle implements IWorldMap, IPositionChangeObserver, IAnimalPlacedPublisher {
    private final int width;
    private final int height;

    private final Vector2D lowerLeft;
    private final Vector2D upperRight;

    private final Vector2D jungleLowerLeft;
    private final Vector2D jungleUpperRight;

    private final HashMap<Vector2D, Grass> grassHashMap = new HashMap<>();
    private final HashMap<Vector2D, LinkedList<Animal>> animalHashMap = new HashMap<>();

    private final LinkedList<Grass> grassLinkedList = new LinkedList<>();
    private final LinkedList<Animal> animalLinkedList = new LinkedList<>();

    private final LinkedList<Vector2D> steppeEmptyFields = new LinkedList<>();
    private final LinkedList<Vector2D> jungleEmptyFields = new LinkedList<>();

    private final HashMap<int[], Integer> animalsGenomes = new HashMap<>();

    private final ArrayList<IAnimalPlacedObserver> animalPlacedObservers = new ArrayList<>();

    public SteppeMapWithJungle(int width, int height, double jungleRatio) {
        this.width = width;
        this.height = height;
        this.upperRight = new Vector2D(width - 1, height - 1);
        this.lowerLeft = new Vector2D(0, 0);
        int jungleWidth = (int) (jungleRatio * width);
        int jungleHeight = (int) (jungleRatio * height);

        int xJungleLowerLeft, yJungleLowerLeft;

        xJungleLowerLeft = width % 2 == 0 ? ((width / 2) - (jungleWidth / 2)) - 1 : ((width / 2) + 1 - (jungleWidth / 2)) - 1;
        yJungleLowerLeft = height % 2 == 0 ? ((height / 2) - (jungleHeight / 2)) - 1 : ((height / 2) + 1 - (jungleHeight / 2)) - 1;

        this.jungleLowerLeft = new Vector2D(xJungleLowerLeft, yJungleLowerLeft);
        this.jungleUpperRight = jungleLowerLeft.add(new Vector2D(jungleWidth, jungleHeight));

        generateMapEmptyFields();
    }

    private void generateMapEmptyFields() {
        for (int i = lowerLeft.x; i <= upperRight.x; i++) {
            for (int j = lowerLeft.y; j <= upperRight.y; j++) {
                Vector2D field = new Vector2D(i, j);
                if (fieldIsInJungle(field)) {
                    jungleEmptyFields.add(field);
                } else {
                    steppeEmptyFields.add(field);
                }
            }
        }
    }

    private boolean fieldIsInJungle(Vector2D field) {
        return field.follows(jungleLowerLeft) && field.precedes(jungleUpperRight);
    }

    public void removePositionFromEmptyPositions(Vector2D position) {
        if (fieldIsInJungle(position)) {
            jungleEmptyFields.remove(position);
        } else {
            steppeEmptyFields.remove(position);
        }
    }

    public void addPositionToEmptyPositions(Vector2D position) {
        if (fieldIsInJungle(position)) {
            jungleEmptyFields.add(position);
        } else {
            steppeEmptyFields.add(position);
        }
    }

    @Override
    public void positionChanged(Animal movedAnimal, Vector2D oldPosition, Vector2D newPosition) {
        newPosition = checkAndFixPositionOutOfMap(newPosition);

        removeAnimalFromHashMap(movedAnimal);

        if (isOccupiedByAnimal(newPosition)) {
            animalHashMap.get(newPosition).add(movedAnimal);
        } else {
            if (!isOccupiedByGrass(newPosition)) {
                removePositionFromEmptyPositions(newPosition);
            }
            animalHashMap.put(newPosition, new LinkedList<>(Collections.singletonList(movedAnimal)));
        }
        movedAnimal.setPosition(newPosition);

        if (!isOccupied(oldPosition)) {
            addPositionToEmptyPositions(oldPosition);
        }
    }

    public Vector2D checkAndFixPositionOutOfMap(Vector2D position) {
        int xPosition = ((position.x % width) + width) % width;
        int yPosition = ((position.y % height) + height) % height;
        return new Vector2D(xPosition, yPosition);
    }

    @Override
    public void place(Animal animal) {
        Vector2D animalPosition = animal.getPosition();
        int[] animalGenome = animal.getAnimalGenes().getGenomeStatistics();
        animalPosition = checkAndFixPositionOutOfMap(animalPosition);
        animal.setPosition(animalPosition);

        if (isOccupiedByAnimal(animalPosition)) {
            animalHashMap.get(animalPosition).add(animal);
        } else {
            if (!isOccupiedByGrass(animalPosition)) {
                removePositionFromEmptyPositions(animalPosition);
            }
            animalHashMap.put(animalPosition, new LinkedList<>(Collections.singletonList(animal)));
        }

        if (animalsGenomes.containsKey(animalGenome)) {
            int animalsWithThisGenome = animalsGenomes.get(animalGenome);
            animalsGenomes.remove(animalGenome);
            animalsGenomes.put(animalGenome, animalsWithThisGenome + 1);
        } else {
            animalsGenomes.put(animalGenome, 1);
        }
        animalLinkedList.add(animal);
        animal.addObserver(this);
        animalPlaced(animal);
    }

    @Override
    public boolean isOccupied(Vector2D position) {
        return isOccupiedByAnimal(position) || isOccupiedByGrass(position);
    }

    public boolean isOccupiedByAnimal(Vector2D position) {
        return animalHashMap.containsKey(position);
    }

    public boolean isOccupiedByGrass(Vector2D position) {
        return grassHashMap.containsKey(position);
    }

    private void removeAnimalFromHashMap(Animal animal) {
        Vector2D animalPosition = animal.getPosition();
        animalHashMap.get(animalPosition).remove(animal);
        if (animalHashMap.get(animalPosition).size() == 0) {
            animalHashMap.remove(animalPosition);
        }
    }

    private void removeAnimalsGenome(Animal animal) {
        int[] animalGenome = animal.getAnimalGenes().getGenomeStatistics();
        if (animalsGenomes.get(animalGenome) - 1 == 0) {
            animalsGenomes.remove(animalGenome);
        } else {
            int animalsWithThisGenome = animalsGenomes.get(animalGenome);
            animalsGenomes.remove(animalGenome);
            animalsGenomes.put(animalGenome, animalsWithThisGenome - 1);
        }
    }

    private void removeAnimalFromLinkedList(Animal animal) {
        animalLinkedList.remove(animal);
    }

    private void removeMapFromElementObservers(IPositionChangedPublisher element) {
        element.removeObserver(this);
    }

    public void removeAnimalCompletely(Animal animal) {
        removeAnimalFromHashMap(animal);
        removeAnimalFromLinkedList(animal);
        removeMapFromElementObservers(animal);
        removeAnimalsGenome(animal);
    }

    public void removeGrassCompletely(Grass grass) {
        grassLinkedList.remove(grass);
        grassHashMap.remove(grass.getPosition());
    }

    public Animal getMaxEnergyAnimalFromField(Vector2D position) {
        LinkedList<Animal> animals = new LinkedList<>(animalHashMap.get(position));
        int maxEnergy = 0;
        for (Animal animal : animals) {
            if (animal.getCurrentEnergy() > maxEnergy) {
                maxEnergy = animal.getCurrentEnergy();
            }
        }

        Animal maxAnimal = null;
        for (Animal animal : animals) {
            if (animal.getCurrentEnergy() == maxEnergy) {
                maxAnimal = animal;
                break;
            }
        }
        return maxAnimal;
    }

    public HashMap<int[], Integer> getAnimalsGenomes() {
        return animalsGenomes;
    }

    public int getAnimalCount() {
        return animalLinkedList.size();
    }

    public HashMap<Vector2D, Grass> getGrassHashMap() {
        return grassHashMap;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPlantCount() {
        return grassLinkedList.size();
    }

    public LinkedList<Grass> getGrassLinkedList() {
        return grassLinkedList;
    }

    public LinkedList<Animal> getAnimalLinkedList() {
        return animalLinkedList;
    }

    public HashMap<Vector2D, LinkedList<Animal>> getAnimalHashMap() {
        return animalHashMap;
    }

    public LinkedList<Vector2D> getSteppeEmptyFields() {
        return steppeEmptyFields;
    }

    public LinkedList<Vector2D> getJungleEmptyFields() {
        return jungleEmptyFields;
    }

    @Override
    public void addObserver(IAnimalPlacedObserver observer) {
        animalPlacedObservers.add(observer);
    }

    @Override
    public void removeObserver(IAnimalPlacedObserver observer) {
        animalPlacedObservers.remove(observer);
    }

    private void animalPlaced(Animal animal) {
        for (IAnimalPlacedObserver observer : animalPlacedObservers) {
            observer.animalPlaced(animal);
        }
    }
}
