package Interfaces;

import Enums.Icon;
import MapElementsClasses.Vector2D;

public interface IMapElement {
    Vector2D getPosition();

    String toString();

    Icon getIcon();
}
