package Interfaces;

import MapElementsClasses.Animal;
import MapElementsClasses.Vector2D;

public interface IWorldMap {
    void place(Animal animal);

    boolean isOccupied(Vector2D position);
}
