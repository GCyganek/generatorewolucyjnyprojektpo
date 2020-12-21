package Visualisation.Settings;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class WrongInputInfo extends Stage {

    public WrongInputInfo(String errorText) {
        setTitle("WRONG INPUT");

        Label errorMessage = new Label(errorText);
        errorMessage.setTextAlignment(TextAlignment.CENTER);
        errorMessage.setWrapText(true);
        errorMessage.setAlignment(Pos.CENTER);

        Scene errorScene = new Scene(errorMessage, 300, 75);

        setScene(errorScene);
        setAlwaysOnTop(true);
    }
}
