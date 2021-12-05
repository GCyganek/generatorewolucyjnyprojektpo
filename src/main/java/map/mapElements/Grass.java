package map.mapElements;

import map.mapElements.util.Icon;
import map.mapElements.util.Vector2D;

public class Grass extends AbstractWorldMapElement {
    public Grass(Vector2D position) {
        this.position = position;
    }

    @Override
    public Icon getIcon() {
        return Icon.GRASS;
    }
}
