package SimulationTests;

import map.mapElements.util.MapDirection;
import map.mapElements.Animal;
import map.mapElements.util.Vector2D;
import simulation.SimulationOperations;
import map.SteppeMapWithJungle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationOperationsTest {
    SteppeMapWithJungle map = new SteppeMapWithJungle(5, 5, 0.2);
    SimulationOperations simulationOperations = new SimulationOperations(map, 20, 40);


    @Test
    void energyChange() {
        Animal animal1 = new Animal(map, 40, MapDirection.WEST, new Vector2D(5, 5), 0);
        Animal animal2 = new Animal(map, 40, MapDirection.WEST, new Vector2D(5, 5), 0);
        Animal animal3 = new Animal(map, 40, MapDirection.WEST, new Vector2D(5, 5), 0);

        simulationOperations.oneDayAnimalsEnergyChange(-1);

        for (Animal animal: map.getAnimalLinkedList()) {
            assertEquals(39, animal.getCurrentEnergy());
        }
    }

    @Test
    void placeGrass() {
        simulationOperations.placeGrass();

        assertEquals(2, map.getGrassLinkedList().size());
        assertTrue(map.getGrassHashMap().containsKey(map.getGrassLinkedList().get(0).getPosition()));
        assertTrue(map.getGrassHashMap().containsKey(map.getGrassLinkedList().get(1).getPosition()));
    }

    @Test
    void eating() {
        simulationOperations.placeGrass();
        Animal animal = new Animal(map, 40, MapDirection.WEST, map.getGrassLinkedList().get(0)
                .getPosition(), 0);

        animal.setCurrentEnergy(20);
        assertEquals(20, animal.getCurrentEnergy());

        simulationOperations.animalsEating();

        assertEquals(40, animal.getCurrentEnergy());
        assertEquals(1, map.getGrassLinkedList().size());
        assertFalse(map.getGrassHashMap().containsKey(animal.getPosition()));
    }

    @Test
    void threeStrongAnimalsAndOneWeakEating() {
        simulationOperations.placeGrass();
        Animal animal1 = new Animal(map, 40, MapDirection.WEST, map.getGrassLinkedList().get(0)
                .getPosition(), 0);
        Animal animal2 = new Animal(map, 40, MapDirection.WEST, map.getGrassLinkedList().get(0)
                .getPosition(), 0);
        Animal animal3 = new Animal(map, 40, MapDirection.WEST, map.getGrassLinkedList().get(0)
                .getPosition(), 0);
        Animal animal4 = new Animal(map, 40, MapDirection.WEST, map.getGrassLinkedList().get(0)
                .getPosition(), 0);
        Animal animal5 = new Animal(map, 40, MapDirection.WEST, map.getGrassLinkedList().get(0)
                .getPosition(), 0);

        animal1.setCurrentEnergy(35);
        animal2.setCurrentEnergy(35);
        animal3.setCurrentEnergy(35);
        animal4.setCurrentEnergy(35);
        animal5.setCurrentEnergy(20);

        simulationOperations.animalsEating();

        assertEquals(40, animal1.getCurrentEnergy());
        assertEquals(40, animal2.getCurrentEnergy());
        assertEquals(40, animal3.getCurrentEnergy());
        assertEquals(40, animal4.getCurrentEnergy());
        assertEquals(20, animal5.getCurrentEnergy());

        assertFalse(map.getGrassHashMap().containsKey(animal1.getPosition()));
    }

    @Test
    void copulation() {

        Animal animal1 = new Animal(map, 40, MapDirection.WEST, new Vector2D(5, 5), 0);
        Animal animal2 = new Animal(map, 40, MapDirection.WEST, new Vector2D(5, 5), 0);
        Animal animal3 = new Animal(map, 40, MapDirection.WEST, new Vector2D(5, 5), 0);
        Animal animal4 = new Animal(map, 40, MapDirection.WEST, new Vector2D(5, 5), 0);

        animal1.setCurrentEnergy(30);
        animal2.setCurrentEnergy(35);
        animal3.setCurrentEnergy(35);
        animal4.setCurrentEnergy(20);

        simulationOperations.copulation(5);

        assertEquals(1, animal2.getChildren().size());
        assertEquals(1, animal3.getChildren().size());
        assertEquals(0, animal1.getChildren().size());
        assertEquals(0, animal4.getChildren().size());

        assertEquals(26, animal2.getCurrentEnergy());
        assertEquals(26, animal3.getCurrentEnergy());
        assertEquals(30, animal1.getCurrentEnergy());
        assertEquals(20, animal4.getCurrentEnergy());


        assertEquals(18, map.getAnimalLinkedList().get(4).getCurrentEnergy());
        assertEquals(5, map.getAnimalLinkedList().get(4).getBirthDay());
    }

}