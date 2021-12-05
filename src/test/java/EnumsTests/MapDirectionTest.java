package EnumsTests;

import map.mapElements.util.MapDirection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class MapDirectionTest {

    @Test
    void next() {
        MapDirection direction = MapDirection.NORTH;
        assertEquals(direction.next(), MapDirection.NORTHEAST);
        direction = direction.next();
        assertEquals(direction.next(), MapDirection.EAST);
        direction = direction.next();
        assertEquals(direction.next(), MapDirection.SOUTHEAST);
        direction = direction.next();
        assertEquals(direction.next(), MapDirection.SOUTH);
        direction = direction.next();
        assertEquals(direction.next(), MapDirection.SOUTHWEST);
        direction = direction.next();
        assertEquals(direction.next(), MapDirection.WEST);
        direction = direction.next();
        assertEquals(direction.next(), MapDirection.NORTHWEST);
        direction = direction.next();
        assertEquals(direction.next(), MapDirection.NORTH);
    }

    @Test
    void previous() {
        MapDirection direction = MapDirection.NORTH;
        assertEquals(direction.previous(), MapDirection.NORTHWEST);
        direction = direction.previous();
        assertEquals(direction.previous(), MapDirection.WEST);
        direction = direction.previous();
        assertEquals(direction.previous(), MapDirection.SOUTHWEST);
        direction = direction.previous();
        assertEquals(direction.previous(), MapDirection.SOUTH);
        direction = direction.previous();
        assertEquals(direction.previous(), MapDirection.SOUTHEAST);
        direction = direction.previous();
        assertEquals(direction.previous(), MapDirection.EAST);
        direction = direction.previous();
        assertEquals(direction.previous(), MapDirection.NORTHEAST);
        direction = direction.previous();
        assertEquals(direction.previous(), MapDirection.NORTH);
    }
}