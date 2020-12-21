package MapElementsClasses;

import AbstractClasses.AbstractWorldMapElement;
import Enums.Icon;
import Enums.MapDirection;
import Enums.MoveDirection;
import Interfaces.IWorldMap;
import Interfaces.Observers.IPositionChangeObserver;
import Interfaces.Publishers.IPositionChangedPublisher;

import java.util.ArrayList;
import java.util.LinkedList;

public class Animal extends AbstractWorldMapElement implements IPositionChangedPublisher {
    public static final int NUMBER_OF_GENES = 32;
    public static final int NUMBER_OF_TYPES_OF_GENES = 8;
    private final int startEnergy;
    private final ArrayList<IPositionChangeObserver> positionObservers = new ArrayList<>();
    private final Genes genes;
    private final long birthDay;
    private final LinkedList<Animal> children = new LinkedList<>();
    private MapDirection orientation;
    private int currentEnergy;
    private long deathDay = -1;

    public Animal(IWorldMap map, int startEnergy, MapDirection orientation, Vector2D initialPosition, long birthDay) {
        this.startEnergy = startEnergy;
        this.currentEnergy = startEnergy;
        this.orientation = orientation;
        this.position = initialPosition;
        this.birthDay = birthDay;
        this.genes = new Genes(NUMBER_OF_GENES, NUMBER_OF_TYPES_OF_GENES);

        map.place(this);
    }

    public Animal(IWorldMap map, int startEnergy, MapDirection orientation, Vector2D initialPosition, long birthDay, Genes childGenes) {
        this.startEnergy = startEnergy;
        this.currentEnergy = startEnergy;
        this.orientation = orientation;
        this.position = initialPosition;
        this.birthDay = birthDay;
        this.genes = childGenes;

        map.place(this);
    }

    public void setPosition(Vector2D newPosition) {
        position = newPosition;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public void move(MoveDirection direction) {
        switch (direction) {
            case RIGHT -> orientation = orientation.next();
            case LEFT -> orientation = orientation.previous();
            case FORWARD -> {
                Vector2D newPosition = position.add(orientation.toUnitVector());
                positionChanged(position, newPosition);
            }
            case BACKWARD -> {
                Vector2D newPosition = position.subtract(orientation.toUnitVector());
                positionChanged(position, newPosition);
            }
        }
    }

    public void rotate() {
        int rotations = genes.returnRandomGene();
        while (rotations != 0) {
            rotations -= 1;
            move(MoveDirection.RIGHT);
        }
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public void setCurrentEnergy(int energy) {
        currentEnergy = energy;
    }

    public void changeEnergy(int value) {
        currentEnergy += value;
        if (currentEnergy > startEnergy) {
            currentEnergy = startEnergy;
        }
    }

    public boolean isDead() {
        return currentEnergy <= 0;
    }

    public void animalDied(long day) {
        deathDay = day;
    }

    public boolean canCopulate() {
        return currentEnergy >= startEnergy / 2;
    }

    public void hasTakenPartInCopulation(Animal child) {
        changeEnergy((int) Math.floor((double) -currentEnergy / 4));
        children.add(child);
    }

    public Genes childGenes(Animal animalMother) {
        return new Genes(this.genes, animalMother.genes);
    }

    @Override
    public void addObserver(IPositionChangeObserver observer) {
        positionObservers.add(observer);
    }

    @Override
    public void removeObserver(IPositionChangeObserver observer) {
        positionObservers.remove(observer);
    }

    private void positionChanged(Vector2D oldPosition, Vector2D newPosition) {
        for (IPositionChangeObserver observer : positionObservers) {
            observer.positionChanged(this, oldPosition, newPosition);
        }
    }

    @Override
    public Icon getIcon() {
        if (currentEnergy >= (2 * startEnergy / 3)) {
            return Icon.ANIMAL_HIGH_ENERGY;
        } else if (currentEnergy >= (startEnergy / 3)) {
            return Icon.ANIMAL_MEDIUM_ENERGY;
        }
        return Icon.ANIMAL_LOW_ENERGY;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "position=" + position +
                '}';
    }

    public LinkedList<Animal> getChildren() {
        return children;
    }

    public long getLifeDuration() {
        return deathDay - birthDay;
    }

    public long getBirthDay() {
        return birthDay;
    }

    public long getDeathDay() {
        return deathDay;
    }

    public Genes getAnimalGenes() {
        return genes;
    }

}
