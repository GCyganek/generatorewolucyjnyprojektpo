package map;

import map.mapElements.Animal;
import map.mapElements.util.Vector2D;

public interface IWorldMap {
    void place(Animal animal);

    boolean isOccupied(Vector2D position);
}
