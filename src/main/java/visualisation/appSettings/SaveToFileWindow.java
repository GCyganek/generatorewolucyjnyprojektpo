package visualisation.appSettings;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class SaveToFileWindow extends Stage {
    private int textFieldValue = -1;

    public SaveToFileWindow(long simulationDay) {

        Label information = new Label("Put in simulation day number on which you want to save the statistics of this simulations.");
        labelSettings(information);

        HBox textFieldContainer = new HBox();
        textFieldContainer.setPrefWidth(350);

        TextField numberOfDays = new TextField();
        numberOfDays.setPromptText("Put in simulation day number");
        textFieldSettings(numberOfDays);

        textFieldContainer.getChildren().add(numberOfDays);
        textFieldContainer.setAlignment(Pos.CENTER);

        Button saveButton = new Button("SAVE");
        saveButton.setAlignment(Pos.CENTER);
        saveButton.setPrefWidth(100);

        HBox buttonsContainer = new HBox(20);
        buttonsContainer.getChildren().add(saveButton);
        buttonsContainer.setAlignment(Pos.CENTER);

        VBox content = new VBox(30);
        content.getChildren().addAll(information, textFieldContainer, buttonsContainer);
        content.setAlignment(Pos.CENTER);

        Scene newScene = new Scene(content, 450, 200);

        setTitle("Save to file");
        setAlwaysOnTop(true);
        setScene(newScene);

        saveButton.setOnAction(e -> {
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
    }

    private void labelSettings(Label label) {
        label.setWrapText(true);
        label.setMaxWidth(400);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
    }

    private void textFieldSettings(TextField textField) {
        textField.setPrefWidth(350);
        textField.setAlignment(Pos.CENTER);
        textField.setFocusTraversable(false);
    }

    public int getTextFieldValue() {
        return textFieldValue;
    }
}
