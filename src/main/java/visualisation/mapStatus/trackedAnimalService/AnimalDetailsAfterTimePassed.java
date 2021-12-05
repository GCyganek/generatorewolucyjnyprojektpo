package visualisation.mapStatus.trackedAnimalService;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import map.mapElements.Animal;
import simulation.Simulation;
import simulation.util.ITimePassedObserver;

public class AnimalDetailsAfterTimePassed extends Stage implements ITimePassedObserver {
    private final Animal trackedAnimal;
    private final Simulation simulation;
    private final Label deathInfo;
    private final Label lifeDuration;
    private final Label childrenCount;
    private final Label simulationLabel;

    public AnimalDetailsAfterTimePassed(Animal trackedAnimal, Simulation simulation) {
        this.trackedAnimal = trackedAnimal;
        this.simulation = simulation;

        VBox details = new VBox(20);

        this.simulationLabel = new Label();
        labelSettings(simulationLabel);
        simulationLabel.setFont(new Font("Tahoma", 15));

        this.deathInfo = new Label();
        labelSettings(deathInfo);

        this.lifeDuration = new Label();

        labelSettings(lifeDuration);

        this.childrenCount = new Label();

        details.getChildren().addAll(simulationLabel, deathInfo, lifeDuration, childrenCount);
        details.setAlignment(Pos.CENTER);

        Scene detailsScene = new Scene(details, 400, 200);

        setTitle("Tracked Animal Details");
        setScene(detailsScene);
    }

    private void labelSettings(Label label) {
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        label.setMaxWidth(400);
    }

    @Override
    public void timePassed() {
        deathInfo.setText(trackedAnimal.getDeathDay() == -1 ? "Animal has not died yet." :
                "Animal died on day number " + trackedAnimal.getDeathDay() + ". [*]");
        labelSettings(deathInfo);

        lifeDuration.setText(trackedAnimal.getDeathDay() == -1 ? "Birth day: " + trackedAnimal.getBirthDay() :
                "Birth day: " + trackedAnimal.getBirthDay() + " Death day: " + trackedAnimal.getDeathDay() + "\nHad " +
                        trackedAnimal.getLifeDuration() + " days of peaceful life.");

        childrenCount.setText("Tracked animal children count: " + trackedAnimal.getChildren().size());

        simulationLabel.setText("Info for animal tracked in simulation " + simulation.getId() + " after " +
                simulation.getSimulationDay() + " days");
        show();
    }
}
