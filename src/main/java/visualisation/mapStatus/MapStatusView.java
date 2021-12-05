package visualisation.mapStatus;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import map.SteppeMapWithJungle;
import simulation.Simulation;
import simulation.statistics.SimulationDailyStatistics;
import visualisation.IUpdatedView;
import visualisation.appSettings.IDominantGenomeButtonClickedObserver;
import visualisation.mapStatus.map.MapView;
import visualisation.mapStatus.statistics.SimulationStatisticsDetailsView;

public class MapStatusView extends VBox implements IUpdatedView, IDominantGenomeButtonClickedObserver {
    private final MapView mapView;
    private final SimulationStatisticsDetailsView simulationStatisticsDetailsView;
    private final SimulationDailyStatistics simulationDailyStatistics;

    public MapStatusView(Simulation simulation, double prefWidth, double prefHeight) {
        setPrefSize(prefWidth, prefHeight);
        setMaxSize(prefWidth, prefHeight);
        setAlignment(Pos.TOP_CENTER);

        this.simulationDailyStatistics = simulation.getSimulationStatistics();
        this.mapView = new MapView(simulation, prefWidth, prefHeight * 8.75 / 10);
        this.simulationStatisticsDetailsView = new SimulationStatisticsDetailsView(simulation, prefWidth, prefHeight * 1.25 / 10);

        getChildren().addAll(simulationStatisticsDetailsView, mapView);
    }

    public void initialize(SteppeMapWithJungle map) {
        mapView.initialize(map);
    }

    public void update() {
        mapView.update();
        simulationStatisticsDetailsView.update();
    }

    @Override
    public void showDominantGenomeAnimals() {
        mapView.showDominantGenomeAnimals(this.simulationDailyStatistics);
    }
}
