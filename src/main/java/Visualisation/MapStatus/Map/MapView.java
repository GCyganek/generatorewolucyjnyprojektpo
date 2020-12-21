package Visualisation.MapStatus.Map;

import InputData.Configurations;
import InputData.GetJsonData;
import Interfaces.IUpdatedView;
import Interfaces.Observers.IFieldClickedObserver;
import Interfaces.Observers.ITimePassedObserver;
import MapElementsClasses.Animal;
import MapElementsClasses.Vector2D;
import Simulation.Simulation;
import Simulation.SimulationDailyStatistics;
import Visualisation.MapStatus.TrackedAnimal.AnimalDetailsAfterTimePassed;
import Visualisation.MapStatus.TrackedAnimal.ShowGenomeForClickedAnimal;
import Visualisation.MapStatus.TrackedAnimal.TrackedAnimalPopUpWindow;
import WorldClasses.SteppeMapWithJungle;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

import java.util.HashSet;

public class MapView extends GridPane implements IUpdatedView, IFieldClickedObserver, ITimePassedObserver {
    private final double cellSideLength;
    private final Simulation simulation;
    MapFieldView[][] positions;
    private SteppeMapWithJungle map;
    private boolean fieldClicked = false;

    public MapView(Simulation simulation, double prefWidth, double prefHeight) {
        this.simulation = simulation;

        setPrefWidth(prefWidth);
        setPrefHeight(prefHeight);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: grey;");

        Configurations config = GetJsonData.getConfig();
        this.cellSideLength = Math.floor(Math.min(prefWidth / config.getWidth(), prefHeight / config.getHeight()));
    }

    public void initialize(SteppeMapWithJungle map) {
        this.map = map;

        positions = new MapFieldView[map.getWidth()][map.getHeight()];

        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                positions[i][j] = new MapFieldView(cellSideLength, i, j);
                positions[i][j].addObserver(this);
                add(positions[i][j], i + 1, j + 1);
            }
        }
    }

    public void update() {
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                Vector2D currentPosition = new Vector2D(i, j);

                if (map.isOccupied(currentPosition)) {
                    if (map.isOccupiedByGrass(currentPosition)) {
                        positions[i][j].updateIcon(map.getGrassHashMap().get(currentPosition));
                    } else {
                        positions[i][j].updateIcon(map.getMaxEnergyAnimalFromField(currentPosition));
                    }
                } else {
                    positions[i][j].updateIcon(null);
                }
            }
        }
    }

    public void showDominantGenomeAnimals(SimulationDailyStatistics simulationDailyStatistics) {
        if (!map.getAnimalLinkedList().isEmpty()) {
            HashSet<Vector2D> positionsWithDominantGenome = simulationDailyStatistics.getPositionsWithDominantGenome();

            for (Vector2D position : positionsWithDominantGenome) {
                positions[position.x][position.y].changeToDominantGenomeAnimal();
            }
        }
    }

    @Override
    public void fieldClicked(Vector2D field) {
        if (!simulation.getStatus() && map.isOccupiedByAnimal(field)) {
            Animal trackedAnimal = map.getMaxEnergyAnimalFromField(field);

            if (!fieldClicked) {
                fieldClicked = true;

                TrackedAnimalPopUpWindow window = new TrackedAnimalPopUpWindow(trackedAnimal, simulation.getSimulationDay());
                window.showAndWait();

                if (window.getTextFieldValue() != -1) {
                    AnimalDetailsAfterTimePassed animalDetails = new AnimalDetailsAfterTimePassed(trackedAnimal, simulation);
                    simulation.addObserver(animalDetails, window.getTextFieldValue());
                    simulation.addObserver(MapView.this, window.getTextFieldValue());
                } else {
                    fieldClicked = false;
                }
            } else {
                ShowGenomeForClickedAnimal window = new ShowGenomeForClickedAnimal(trackedAnimal);
                window.show();
            }
        }
    }

    @Override
    public void timePassed() {
        fieldClicked = false;
    }
}
