package MapElementsClassesTests;

import MapElementsClasses.Vector2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2DTest {

    @Test
    public void testTwo2dVectorsEqual() {
        Vector2D vector = new Vector2D(1, 3);
        Vector2D otherVector = new Vector2D(1, 3);
        assertEquals(otherVector, vector);
    }

    @Test
    public void testTwo2dVectorsNotEqual() {
        Vector2D vector = new Vector2D(1, 3);
        Vector2D otherVector = new Vector2D(5, 4);
        assertNotEquals(otherVector, vector);
    }

    @Test
    public void testVector2dEqualsToItself() {
        Vector2D vector = new Vector2D(1, 3);
        assertEquals(vector, vector);
    }

    @Test
    public void testToString() {
        Vector2D vector = new Vector2D(1, 3);
        assertEquals("[1, 3]", vector.toString());
    }

    @Test
    public void testPrecedesBothValuesLessOrEqual() {
        Vector2D vector = new Vector2D(1, 4);
        Vector2D otherVector = new Vector2D(5, 4);
        assertTrue(vector.precedes(otherVector));
    }

    @Test
    public void testPrecedesOneValueLessOrEqual() {
        Vector2D vector = new Vector2D(6, 3);
        Vector2D otherVector = new Vector2D(5, 4);
        assertFalse(vector.precedes(otherVector));
    }

    @Test
    public void testPrecedesBothValuesNotLessOrEqual() {
        Vector2D vector = new Vector2D(6, 5);
        Vector2D otherVector = new Vector2D(5, 4);
        assertFalse(vector.precedes(otherVector));
    }

    @Test
    public void testFollowsBothBiggerOrEqual() {
        Vector2D vector = new Vector2D(5, 6);
        Vector2D otherVector = new Vector2D(5, 4);
        assertTrue(vector.follows(otherVector));
    }

    @Test
    public void testFollowsOneValueBiggerOrEqual() {
        Vector2D vector = new Vector2D(5, 3);
        Vector2D otherVector = new Vector2D(5, 4);
        assertFalse(vector.follows(otherVector));
    }

    @Test
    public void testFollowsBothValuesNotBiggerOrEqual() {
        Vector2D vector = new Vector2D(1, 1);
        Vector2D otherVector = new Vector2D(5, 4);
        assertFalse(vector.follows(otherVector));
    }

    @Test
    public void testUpperRight() {
        Vector2D vector = new Vector2D(1, 5);
        Vector2D otherVector = new Vector2D(5, 2);
        assertEquals(vector.upperRight(otherVector), new Vector2D(5, 5));
    }

    @Test
    public void testLowerLeft() {
        Vector2D vector = new Vector2D(1, 5);
        Vector2D otherVector = new Vector2D(5, 2);
        assertEquals(vector.lowerLeft(otherVector), new Vector2D(1, 2));
    }

    @Test
    public void testAdd() {
        Vector2D vector = new Vector2D(1, 5);
        Vector2D otherVector = new Vector2D(5, 2);
        assertEquals(vector.add(otherVector), new Vector2D(6, 7));
    }

    @Test
    public void testSubstract() {
        Vector2D vector = new Vector2D(1, 5);
        Vector2D otherVector = new Vector2D(5, 2);
        assertEquals(vector.subtract(otherVector), new Vector2D(-4, 3));
    }

    @Test
    public void testOpposite() {
        Vector2D vector = new Vector2D(1, 5);
        assertEquals(vector.opposite(), new Vector2D(-1, -5));
    }
}