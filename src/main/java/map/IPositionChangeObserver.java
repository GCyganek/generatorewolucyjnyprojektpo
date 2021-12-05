package map;

import map.mapElements.Animal;
import map.mapElements.util.Vector2D;

public interface IPositionChangeObserver {
    void positionChanged(Animal movedAnimal, Vector2D oldPosition, Vector2D newPosition);
}
