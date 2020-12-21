package MapElementsClasses;

import java.util.Objects;

public class Vector2D implements Comparable<Vector2D> {
    public final int x;
    public final int y;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Vector2D vector2d = (Vector2D) other;
        return x == vector2d.x &&
                y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    public boolean precedes(Vector2D other) {
        return x <= other.x && y <= other.y;
    }

    public boolean follows(Vector2D other) {
        return x >= other.x && y >= other.y;
    }

    public Vector2D upperRight(Vector2D other) {
        return new Vector2D(Math.max(x, other.x), Math.max(y, other.y));
    }

    public Vector2D lowerLeft(Vector2D other) {
        return new Vector2D(Math.min(x, other.x), Math.min(y, other.y));
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(x + other.x, y + other.y);
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(x - other.x, y - other.y);
    }

    public Vector2D opposite() {
        return new Vector2D(-x, -y);
    }

    @Override
    public int compareTo(Vector2D vector) {
        if (this.follows(vector)) {
            return 1;
        } else if (this.equals(vector)) {
            return 0;
        }
        return -1;
    }
}
