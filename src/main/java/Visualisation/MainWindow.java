package Visualisation;

import FileService.FileManagement;
import FileService.SimulationsStatisticsFilePath;
import Simulation.Simulation;
import Simulations.SimulationsContainer;
import Visualisation.Settings.IntervalSlider;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainWindow extends Stage {

    public MainWindow() {
        setTitle("Generator Ewolucyjny");

        FileManagement.clearFile(SimulationsStatisticsFilePath.path);

        VBox root = new VBox();
        root.setStyle("-fx-background-color: lightgrey;");
        root.setPrefSize(Screen.getPrimary().getVisualBounds().getWidth() - 100, Screen.getPrimary()
                .getVisualBounds().getHeight() - 50);

        Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());

        IntervalSlider intervalSlider = new IntervalSlider(root.getPrefWidth(), 50);
        intervalSlider.setAlignment(Pos.CENTER);

        HBox simulationsContainer = new HBox();
        simulationsContainer.setPrefSize(root.getPrefWidth(), root.getPrefHeight() - 70);

        try {
            SimulationsContainer.createSimulations();
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }

        for (Simulation simulation : SimulationsContainer.getSimulations()) {
            SimulationView simulationView = new SimulationView(simulation, (simulationsContainer.getPrefWidth() /
                    SimulationsContainer.getSimulations().size()), simulationsContainer.getPrefHeight());
            simulationsContainer.getChildren().add(simulationView);
        }

        root.getChildren().addAll(intervalSlider, simulationsContainer);

        setScene(scene);
        setResizable(false);
    }


}
