package Visualisation.MapStatus.Map;

import Enums.Icon;
import Interfaces.IMapElement;
import Interfaces.Observers.IFieldClickedObserver;
import Interfaces.Publishers.IFieldClickedPublisher;
import MapElementsClasses.Vector2D;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;

import java.util.ArrayList;


public class MapFieldView extends ImageView implements IFieldClickedPublisher {
    private final Icon defaultIcon = Icon.GROUND;
    private final Vector2D position;
    private final ArrayList<IFieldClickedObserver> fieldClickedObserver = new ArrayList<>();

    public MapFieldView(double prefLength, int x, int y) {
        this.position = new Vector2D(x, y);

        setFitWidth(prefLength);
        setFitHeight(prefLength);
        setImage(defaultIcon.icon);
        setCursor(Cursor.CROSSHAIR);
        setOnMouseClicked(e -> {
            for (IFieldClickedObserver iFieldClickedObserver : fieldClickedObserver) {
                iFieldClickedObserver.fieldClicked(position);
            }
        });
    }

    public void updateIcon(IMapElement elementOnField) {
        if (elementOnField == null) {
            setImage(defaultIcon.icon);
        } else {
            setImage(elementOnField.getIcon().icon);
        }
    }

    public void changeToDominantGenomeAnimal() {
        setImage(Icon.ANIMAL_DOMINANT_GENOME.icon);
    }

    @Override
    public void addObserver(IFieldClickedObserver observer) {
        fieldClickedObserver.add(observer);
    }

    @Override
    public void removeObserver(IFieldClickedObserver observer) {
        fieldClickedObserver.remove(observer);
    }
}
