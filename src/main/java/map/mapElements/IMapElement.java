package map.mapElements;

import map.mapElements.util.Icon;
import map.mapElements.util.Vector2D;

public interface IMapElement {
    Vector2D getPosition();

    String toString();

    Icon getIcon();
}
