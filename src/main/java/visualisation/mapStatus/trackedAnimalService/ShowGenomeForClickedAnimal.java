package visualisation.mapStatus.trackedAnimalService;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import map.mapElements.Animal;

import java.util.Arrays;

public class ShowGenomeForClickedAnimal extends Stage {

    public ShowGenomeForClickedAnimal(Animal clickedAnimal) {
        Label animalGenome = new Label("Clicked animal genome: \n" + Arrays.toString(clickedAnimal.getAnimalGenes().getGenome()));
        animalGenome.setWrapText(true);
        animalGenome.setMaxWidth(400);
        animalGenome.setTextAlignment(TextAlignment.CENTER);
        animalGenome.setAlignment(Pos.CENTER);

        VBox content = new VBox();
        content.getChildren().add(animalGenome);
        content.setAlignment(Pos.CENTER);

        Scene newScene = new Scene(content, 450, 100);

        setTitle("Clicked animal genome");
        setAlwaysOnTop(true);
        setScene(newScene);
    }

}
