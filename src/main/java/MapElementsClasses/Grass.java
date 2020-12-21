package MapElementsClasses;

import AbstractClasses.AbstractWorldMapElement;
import Enums.Icon;

public class Grass extends AbstractWorldMapElement {
    public Grass(Vector2D position) {
        this.position = position;
    }

    @Override
    public Icon getIcon() {
        return Icon.GRASS;
    }
}
