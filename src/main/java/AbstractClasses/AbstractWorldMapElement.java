package AbstractClasses;

import Interfaces.IMapElement;
import MapElementsClasses.Vector2D;

public abstract class AbstractWorldMapElement implements IMapElement {
    protected Vector2D position;

    public Vector2D getPosition() {
        return position;
    }

}
