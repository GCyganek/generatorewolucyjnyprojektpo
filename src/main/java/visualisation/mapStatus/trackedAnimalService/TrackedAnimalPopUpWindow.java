package visualisation.mapStatus.trackedAnimalService;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import map.mapElements.Animal;
import visualisation.appSettings.WrongInputInfo;

import java.util.Arrays;

public class TrackedAnimalPopUpWindow extends Stage {
    private int textFieldValue = -1;

    public TrackedAnimalPopUpWindow(Animal trackedAnimal, long simulationDay) {
        Label animalGenome = new Label("Tracked animal genome: \n" + Arrays.toString(trackedAnimal
                .getAnimalGenes().getGenome()));
        labelSettings(animalGenome);

        Label information = new Label("If you want to track this animal, set simulation day number to show its " +
                "details, then press 'YES' and wait for days to pass, otherwise press 'NO'");
        labelSettings(information);

        HBox textFieldContainer = new HBox();
        textFieldContainer.setPrefWidth(300);

        TextField numberOfDays = new TextField();
        numberOfDays.setPromptText("Set simulation day number");
        numberOfDays.setPrefWidth(300);
        numberOfDays.setAlignment(Pos.CENTER);
        numberOfDays.setFocusTraversable(false);

        textFieldContainer.getChildren().add(numberOfDays);
        textFieldContainer.setAlignment(Pos.CENTER);

        Button yesButton = new Button("YES");
        yesButton.setAlignment(Pos.CENTER);
        yesButton.setPrefWidth(100);

        Button noButton = new Button("NO");
        noButton.setAlignment(Pos.CENTER);
        noButton.setPrefWidth(100);

        HBox buttonsContainer = new HBox(20);
        buttonsContainer.getChildren().addAll(yesButton, noButton);
        buttonsContainer.setAlignment(Pos.CENTER);

        VBox content = new VBox(30);
        content.getChildren().addAll(animalGenome, information, textFieldContainer, buttonsContainer);
        content.setAlignment(Pos.CENTER);

        Scene newScene = new Scene(content, 450, 300);

        setTitle("Tracked animal");
        setAlwaysOnTop(true);
        setScene(newScene);

        yesButton.setOnAction(e -> {
            String textFieldValue = numberOfDays.getText();
            if (textFieldValue.matches("[1-9][0-9]*") && Integer.parseInt(textFieldValue) > simulationDay) {
                this.textFieldValue = Integer.parseInt(textFieldValue);
                close();
            } else {
                close();

                Stage errorStage = new WrongInputInfo("Wrong input. Amount of days can only be integer value " +
                        "higher than 0 and current simulation day");
                errorStage.show();
            }
        });

        noButton.setOnAction(e -> close());
    }

    private void labelSettings(Label label) {
        label.setWrapText(true);
        label.setMaxWidth(400);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
    }

    public int getTextFieldValue() {
        return textFieldValue;
    }
}
