package MapElementsClassesTests;

import map.mapElements.util.MapDirection;
import map.mapElements.util.MoveDirection;
import map.mapElements.Animal;
import map.mapElements.util.Vector2D;
import map.SteppeMapWithJungle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    SteppeMapWithJungle map = new SteppeMapWithJungle(50, 50, 0.5);

    @Test
    void moveInDifferentDirectionsWithDifferentOrientations() {
        Animal animal = new Animal(map, 40, MapDirection.WEST, new Vector2D(25, 25), 0);
        animal.move(MoveDirection.FORWARD);
        assertEquals(animal.getPosition(), new Vector2D(24, 25));
        animal.move(MoveDirection.RIGHT);
        assertEquals(animal.getOrientation(), MapDirection.NORTHWEST);
        animal.move(MoveDirection.BACKWARD);
        assertEquals(animal.getPosition(), new Vector2D(25, 24));
        animal.move(MoveDirection.LEFT);
        assertEquals(animal.getOrientation(), MapDirection.WEST);
        animal.move(MoveDirection.BACKWARD);
        assertEquals(animal.getPosition(), new Vector2D(26, 24));
        animal.move(MoveDirection.LEFT);
        assertEquals(animal.getOrientation(), MapDirection.SOUTHWEST);
        animal.move(MoveDirection.BACKWARD);
        assertEquals(animal.getPosition(), new Vector2D(27, 25));
    }

    @Test
    void moveOutsideTheMap() {
        Animal animal = new Animal(map, 40, MapDirection.WEST, new Vector2D(0, 25), 0);
        animal.move(MoveDirection.FORWARD);
        assertEquals(animal.getPosition(), new Vector2D(49, 25));
        animal.move(MoveDirection.BACKWARD);
        assertEquals(animal.getPosition(), new Vector2D(0, 25));
    }

    @Test
    void moveOnTheFieldWithAnimal() {
        Animal animal = new Animal(map, 40, MapDirection.WEST, new Vector2D(0, 25), 0);
        Animal animal2 = new Animal(map, 40, MapDirection.WEST, new Vector2D(1, 25), 0);
        animal2.move(MoveDirection.FORWARD);
        assertEquals(animal2.getPosition(), new Vector2D(0, 25));
    }

}