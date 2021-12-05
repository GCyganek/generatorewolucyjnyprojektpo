package simulation;

import map.SteppeMapWithJungle;
import map.mapElements.Animal;
import map.mapElements.Grass;
import map.mapElements.util.MapDirection;
import map.mapElements.util.MoveDirection;
import map.mapElements.util.Vector2D;
import simulation.util.IAnimalDiedObserver;
import simulation.util.IAnimalDiedPublisher;
import util.Random;

import java.util.*;

public class SimulationOperations implements IAnimalDiedPublisher {
    private final SteppeMapWithJungle map;

    private final int plantEnergy;
    private final int animalStartEnergy;

    private final ArrayList<IAnimalDiedObserver> deadAnimalsObservers = new ArrayList<>();

    public SimulationOperations(SteppeMapWithJungle map, int plantEnergy, int animalStartEnergy) {
        this.map = map;
        this.plantEnergy = plantEnergy;
        this.animalStartEnergy = animalStartEnergy;
    }

    public void placeAnimalOnRandomField() {
        LinkedList<Vector2D> steppeEmptyFields = map.getSteppeEmptyFields();
        LinkedList<Vector2D> jungleEmptyFields = map.getJungleEmptyFields();
        Vector2D emptyField;

        if (steppeEmptyFields.size() > 0 && jungleEmptyFields.size() > 0) {
            if (Random.randomNumber(0, 1) == 0) {
                emptyField = steppeEmptyFields.get(Random.randomNumber(0, steppeEmptyFields.size() - 1));
            } else {
                emptyField = jungleEmptyFields.get(Random.randomNumber(0, jungleEmptyFields.size() - 1));
            }
        } else if (steppeEmptyFields.size() > 0) {
            emptyField = steppeEmptyFields.get(Random.randomNumber(0, steppeEmptyFields.size() - 1));
        } else {
            emptyField = jungleEmptyFields.get(Random.randomNumber(0, jungleEmptyFields.size() - 1));
        }

        new Animal(map, animalStartEnergy, MapDirection.randomMapDirection(), emptyField, 0);
    }

    public void placeGrass() {
        LinkedList<Vector2D> steppeEmptyFields = map.getSteppeEmptyFields();
        LinkedList<Vector2D> jungleEmptyFields = map.getJungleEmptyFields();

        if (steppeEmptyFields.size() > 0) {
            Vector2D emptySteppeField = steppeEmptyFields.get(Random.randomNumber(0, steppeEmptyFields.size() - 1));
            map.removePositionFromEmptyPositions(emptySteppeField);
            Grass grass = new Grass(emptySteppeField);
            map.getGrassLinkedList().add(grass);
            map.getGrassHashMap().put(emptySteppeField, grass);
        }
        if (jungleEmptyFields.size() > 0) {
            Vector2D emptyJungleField = jungleEmptyFields.get(Random.randomNumber(0, jungleEmptyFields.size() - 1));
            map.removePositionFromEmptyPositions(emptyJungleField);
            Grass grass = new Grass(emptyJungleField);
            map.getGrassLinkedList().add(grass);
            map.getGrassHashMap().put(emptyJungleField, grass);
        }
    }

    public void runAnimals() {
        for (Animal animal : map.getAnimalLinkedList()) {
            animal.rotate();
            animal.move(MoveDirection.FORWARD);
        }
    }

    public void removeDeadAnimals(long day) {
        LinkedList<Animal> mapAnimalLinkedList = map.getAnimalLinkedList();
        LinkedList<Animal> deadAnimals = new LinkedList<>();

        for (Animal animal : mapAnimalLinkedList) {
            if (animal.isDead()) {
                deadAnimals.add(animal);
            }
        }

        for (Animal animal : deadAnimals) {
            animal.animalDied(day);
            animalDiedPublish(animal);
            map.removeAnimalCompletely(animal);
            if (!map.isOccupied(animal.getPosition())) {
                map.addPositionToEmptyPositions(animal.getPosition());
            }
        }
    }

    public void oneDayAnimalsEnergyChange(int value) {
        LinkedList<Animal> mapAnimalLinkedList = map.getAnimalLinkedList();

        for (Animal animal : mapAnimalLinkedList) {
            animal.changeEnergy(value);
        }
    }

    public void animalsEating() {
        ArrayList<Grass> grassToRemove = new ArrayList<>();
        LinkedList<Grass> mapGrassLinkedList = map.getGrassLinkedList();

        if (!map.getAnimalLinkedList().isEmpty()) {
            for (Grass grass : mapGrassLinkedList) {
                LinkedList<Animal> animalsOnGrass = map.getAnimalHashMap().get(grass.getPosition());
                if (animalsOnGrass != null) {
                    animalsOnGrass = strongestAnimalsOnFieldForEating(animalsOnGrass);
                    for (Animal animal : animalsOnGrass) {
                        animal.changeEnergy(plantEnergy / animalsOnGrass.size());
                    }
                    grassToRemove.add(grass);
                }
            }
        }

        for (Grass grass : grassToRemove) {
            map.removeGrassCompletely(grass);
        }
    }

    private LinkedList<Animal> strongestAnimalsOnFieldForEating(LinkedList<Animal> animalsOnGrass) {
        Animal strongestAnimal = Collections.max(animalsOnGrass, Comparator.comparing(Animal::getCurrentEnergy));
        int highestEnergy = strongestAnimal.getCurrentEnergy();
        LinkedList<Animal> strongestAnimals = new LinkedList<>();
        for (Animal animal : animalsOnGrass) {
            if (animal.getCurrentEnergy() == highestEnergy) {
                strongestAnimals.add(animal);
            }
        }
        return strongestAnimals;
    }

    public void copulation(long day) {
        LinkedList<Vector2D> positions = new LinkedList<>(map.getAnimalHashMap().keySet());

        for (Vector2D position : positions) {
            LinkedList<Animal> animalsOnCurrentPosition = map.getAnimalHashMap().get(position);
            if (animalsOnCurrentPosition.size() > 1) {
                LinkedList<Animal> potentialParentsWithHighestEnergyOnField = new LinkedList<>();
                LinkedList<Animal> potentialParentsWith2ndHighestEnergyOnField = new LinkedList<>();

                int highestEnergy = 0;
                int secondHighestEnergy = 0;

                for (Animal animal : animalsOnCurrentPosition) {
                    if (animal.canCopulate()) {
                        int currentAnimalEnergy = animal.getCurrentEnergy();

                        if (highestEnergy < currentAnimalEnergy) {
                            highestEnergy = currentAnimalEnergy;
                        }

                        if (secondHighestEnergy < currentAnimalEnergy && highestEnergy != currentAnimalEnergy) {
                            secondHighestEnergy = currentAnimalEnergy;
                        }
                    }
                }

                if (highestEnergy == 0) {
                    return;
                }

                for (Animal animal : animalsOnCurrentPosition) {
                    if (animal.getCurrentEnergy() == highestEnergy) {
                        potentialParentsWithHighestEnergyOnField.add(animal);
                    }

                    if (animal.getCurrentEnergy() == secondHighestEnergy) {
                        potentialParentsWith2ndHighestEnergyOnField.add(animal);
                    }
                }

                if (secondHighestEnergy == 0 && potentialParentsWithHighestEnergyOnField.size() < 2) {
                    return;
                }

                Animal animalFather, animalMother;

                if (potentialParentsWithHighestEnergyOnField.size() > 1) {
                    animalFather = potentialParentsWithHighestEnergyOnField.get(Random.randomNumber(0, potentialParentsWithHighestEnergyOnField.size() - 1));
                    potentialParentsWithHighestEnergyOnField.remove(animalFather);
                    animalMother = potentialParentsWithHighestEnergyOnField.get(Random.randomNumber(0, potentialParentsWithHighestEnergyOnField.size() - 1));
                } else {
                    animalFather = potentialParentsWithHighestEnergyOnField.get(0);
                    animalMother = potentialParentsWith2ndHighestEnergyOnField.get(Random.randomNumber(0, potentialParentsWith2ndHighestEnergyOnField.size() - 1));
                }
                animalBirth(animalFather, animalMother, day);
            }
        }
    }

    private void animalBirth(Animal animalFather, Animal animalMother, long birthDay) {
        Vector2D birthField = fieldForNewAnimalAfterCopulation(animalFather.getPosition());
        int childEnergy = (int) Math.ceil(((double) animalFather.getCurrentEnergy()) / 4 + ((double) animalMother.getCurrentEnergy() / 4));
        Animal animalChild = new Animal(map, animalStartEnergy, MapDirection.randomMapDirection(), birthField, birthDay, animalFather.childGenes(animalMother));
        animalFather.hasTakenPartInCopulation(animalChild);
        animalMother.hasTakenPartInCopulation(animalChild);
        animalChild.setCurrentEnergy(childEnergy);
    }


    private Vector2D fieldForNewAnimalAfterCopulation(Vector2D parentsPosition) {
        LinkedList<Vector2D> fields = new LinkedList<>(Arrays.asList(parentsPosition.add(MapDirection.NORTH.toUnitVector()),
                parentsPosition.add(MapDirection.NORTHEAST.toUnitVector()), parentsPosition.add(MapDirection.EAST.toUnitVector()),
                parentsPosition.add(MapDirection.SOUTHEAST.toUnitVector()), parentsPosition.add(MapDirection.SOUTH.toUnitVector()),
                parentsPosition.add(MapDirection.SOUTHWEST.toUnitVector()), parentsPosition.add(MapDirection.WEST.toUnitVector()),
                parentsPosition.add(MapDirection.NORTHWEST.toUnitVector())));

        if (checkIfAllFieldsAreOccupiedByAnimal(fields)) {
            return fields.get(Random.randomNumber(0, fields.size() - 1));
        }

        LinkedList<Vector2D> result = new LinkedList<>();
        for (Vector2D field : fields) {
            if (!map.isOccupiedByAnimal(field)) {
                result.add(field);
            }
        }
        return result.get(Random.randomNumber(0, result.size() - 1));
    }

    private boolean checkIfAllFieldsAreOccupiedByAnimal(LinkedList<Vector2D> positions) {
        for (Vector2D position : positions) {
            if (!map.isOccupiedByAnimal(position)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void addObserver(IAnimalDiedObserver observer) {
        deadAnimalsObservers.add(observer);
    }

    @Override
    public void removeObserver(IAnimalDiedObserver observer) {
        deadAnimalsObservers.remove(observer);
    }

    private void animalDiedPublish(Animal animal) {
        for (IAnimalDiedObserver observer : deadAnimalsObservers) {
            observer.animalDied(animal);
        }
    }

}
