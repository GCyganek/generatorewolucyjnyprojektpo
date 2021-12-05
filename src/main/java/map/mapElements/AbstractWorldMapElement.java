package map.mapElements;

import map.mapElements.util.Vector2D;

public abstract class AbstractWorldMapElement implements IMapElement {
    protected Vector2D position;

    public Vector2D getPosition() {
        return position;
    }

}
