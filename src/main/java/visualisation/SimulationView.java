package visualisation;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import simulation.Simulation;
import visualisation.appSettings.SettingsView;
import visualisation.mapStatus.MapStatusView;

public class SimulationView extends VBox {

    public SimulationView(Simulation simulation, double prefWidth, double prefHeight) {
        setPrefSize(prefWidth, prefHeight);
        setAlignment(Pos.CENTER);
        setStyle("-fx-border-color:lightgrey;");

        SettingsView settingsView = new SettingsView(simulation, prefWidth - 20, prefHeight / 13);
        MapStatusView mapStatusView = new MapStatusView(simulation, prefWidth - 20, prefHeight * 12 / 13);

        simulation.setView(mapStatusView);

        settingsView.addObserver(mapStatusView);

        getChildren().addAll(settingsView, mapStatusView);
    }
}
