package Interfaces.Observers;

import MapElementsClasses.Animal;
import MapElementsClasses.Vector2D;

public interface IPositionChangeObserver {
    void positionChanged(Animal movedAnimal, Vector2D oldPosition, Vector2D newPosition);
}
