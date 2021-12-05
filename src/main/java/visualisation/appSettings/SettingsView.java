package visualisation.appSettings;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import simulation.Simulation;

import java.util.ArrayList;

public class SettingsView extends HBox implements IDominantGenomeButtonClickedPublisher {
    private final ArrayList<IDominantGenomeButtonClickedObserver> dominantGenomeObservers = new ArrayList<>();

    public SettingsView(Simulation simulation, double prefWidth, double prefHeight) {
        setSpacing(3);
        setPrefSize(prefWidth, prefHeight);
        setAlignment(Pos.CENTER);

        Button pauseButton = new Button();

        pauseButton.setText(simulation.getStatus() ? "Pause" : "Simulate");
        pauseButton.setOnAction(event -> {
            simulation.changeSimulationRunning();
            pauseButton.setText(simulation.getStatus() ? "Pause" : "Simulate");
        });

        Button dominantGenomeButton = new Button("Show animals with dominant genome");

        dominantGenomeButton.setOnAction(event -> {
            if (!simulation.getStatus()) {
                dominantGenomeButtonClicked();
            }
        });

        Button fileButton = new Button();

        fileButton.setText("Save to file");
        fileButton.setOnAction(event -> {
            if (!simulation.getStatus()) {
                fileButton.setText("Save to file");
                SaveToFileWindow window = new SaveToFileWindow(simulation.getSimulationDay());
                window.showAndWait();

                int saveToFileDay = window.getTextFieldValue();
                simulation.addToSaveToFileDays(saveToFileDay);
            }
        });

        getChildren().addAll(pauseButton, dominantGenomeButton, fileButton);
    }

    @Override
    public void addObserver(IDominantGenomeButtonClickedObserver observer) {
        dominantGenomeObservers.add(observer);
    }

    @Override
    public void removeObserver(IDominantGenomeButtonClickedObserver observer) {
        dominantGenomeObservers.remove(observer);
    }

    private void dominantGenomeButtonClicked() {
        for (IDominantGenomeButtonClickedObserver observer : dominantGenomeObservers) {
            observer.showDominantGenomeAnimals();
        }
    }
}
