package WorldClassesTests;

import Enums.MapDirection;
import MapElementsClasses.Animal;
import MapElementsClasses.Vector2D;
import WorldClasses.SteppeMapWithJungle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SteppeMapWithJungleTest {
    SteppeMapWithJungle map = new SteppeMapWithJungle(20, 20, 0);

    @Test
    void checkAndFixPositionOutOfMapNoFixNeeded() {
        Vector2D vector = new Vector2D(5, 5);
        assertEquals(new Vector2D(5, 5), map.checkAndFixPositionOutOfMap(vector));

        Vector2D vector1 = new Vector2D(-1, -1);
        assertEquals(new Vector2D(19, 19), map.checkAndFixPositionOutOfMap(vector1));

        Vector2D vector2 = new Vector2D(1, 63);
        assertEquals(new Vector2D(1, 3), map.checkAndFixPositionOutOfMap(vector2));

        Vector2D vector3 = new Vector2D(1, -1);
        assertEquals(new Vector2D(1, 19), map.checkAndFixPositionOutOfMap(vector3));

        Vector2D vector4 = new Vector2D(-1, 1);
        assertEquals(new Vector2D(19, 1), map.checkAndFixPositionOutOfMap(vector4));

        Vector2D vector5 = new Vector2D(20, 20);
        assertEquals(new Vector2D(0, 0), map.checkAndFixPositionOutOfMap(vector5));

        Vector2D vector6 = new Vector2D(20, 1);
        assertEquals(new Vector2D(0, 1), map.checkAndFixPositionOutOfMap(vector6));

        Vector2D vector7 = new Vector2D(1, 20);
        assertEquals(new Vector2D(1, 0), map.checkAndFixPositionOutOfMap(vector7));
    }

    @Test
    void placeAnimal() {
        Animal animal = new Animal(map, 40, MapDirection.WEST, new Vector2D(5, 25), 0);

        assertEquals(new Vector2D(5, 5), animal.getPosition());

        assertTrue(map.getAnimalHashMap().containsKey(animal.getPosition()));

        assertEquals(1, map.getAnimalCount());

        assertEquals(1, map.getAnimalsGenomes().size());

        assertTrue(map.isOccupied(animal.getPosition()));
        assertTrue(map.isOccupiedByAnimal(animal.getPosition()));
        assertFalse(map.isOccupiedByGrass(animal.getPosition()));

    }

    @Test
    void placeSecondAnimalOnTheSameField() {
        Animal animal1 = new Animal(map, 40, MapDirection.WEST, new Vector2D(5, 25), 0);
        Animal animal = new Animal(map, 40, MapDirection.WEST, new Vector2D(5, 25), 0);

        assertTrue(map.getAnimalHashMap().containsKey(animal1.getPosition()));

        assertEquals(2, map.getAnimalCount());

        assertEquals(2, map.getAnimalHashMap().get(animal.getPosition()).size());
    }

    @Test
    void getAnimalWithMaxEnergyFromField() {
        Animal animal1 = new Animal(map, 50, MapDirection.WEST, new Vector2D(5, 25), 0);
        Animal animal2 = new Animal(map, 50, MapDirection.WEST, new Vector2D(5, 25), 0);
        Animal animal3 = new Animal(map, 61, MapDirection.WEST, new Vector2D(5, 25), 0);
        Animal animal4 = new Animal(map, 50, MapDirection.WEST, new Vector2D(5, 25), 0);
        Animal animal5 = new Animal(map, 40, MapDirection.WEST, new Vector2D(5, 25), 0);
        Animal animal6 = new Animal(map, 61, MapDirection.WEST, new Vector2D(5, 25), 0);

        assertEquals(61, map.getMaxEnergyAnimalFromField(animal1.getPosition()).getCurrentEnergy());
    }

    @Test
    void removeAnimalFromMap() {
        Animal animal = new Animal(map, 50, MapDirection.WEST, new Vector2D(5, 25), 0);

        map.removeAnimalCompletely(animal);

        assertFalse(map.getAnimalHashMap().containsKey(animal.getPosition()));

        assertEquals(0, map.getAnimalCount());

        assertEquals(0, map.getAnimalsGenomes().size());

        assertFalse(map.isOccupied(animal.getPosition()));
        assertFalse(map.isOccupiedByAnimal(animal.getPosition()));
        assertFalse(map.isOccupiedByGrass(animal.getPosition()));
    }

}