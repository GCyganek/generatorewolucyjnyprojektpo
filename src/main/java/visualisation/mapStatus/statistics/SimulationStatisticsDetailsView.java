package visualisation.mapStatus.statistics;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import simulation.Simulation;
import simulation.statistics.SimulationDailyStatistics;
import visualisation.IUpdatedView;

public class SimulationStatisticsDetailsView extends VBox implements IUpdatedView {
    private final SimulationDailyStatistics simulationDailyStatistics;
    private final Label simulationDay;
    private final Label animalCount;
    private final Label plantCount;
    private final Label averageEnergy;
    private final Label lifeSpan;
    private final Label childrenCount;
    private final Label genotypeStatisticsValue;

    public SimulationStatisticsDetailsView(Simulation simulation, double prefWidth, double prefHeight) {
        setPrefSize(prefWidth, prefHeight);

        Label title = new Label("Simulation statistics for simulation " + simulation.getId());
        title.setFont(new Font("Tahoma", 15));
        title.setTextFill(Color.web("#0076a3"));
        title.setMinSize(prefWidth, 20);

        HBox detailsBox = new HBox(10);
        this.simulationDailyStatistics = simulation.getSimulationStatistics();

        this.simulationDay = new Label(simulationDailyStatistics.getSimulationDay());
        setLabelsView(simulationDay);

        this.animalCount = new Label(simulationDailyStatistics.getAnimalCount());
        setLabelsView(animalCount);

        this.plantCount = new Label(simulationDailyStatistics.getPlantCount());
        setLabelsView(plantCount);

        this.averageEnergy = new Label(simulationDailyStatistics.getAverageAnimalEnergyForLivingAnimals());
        setLabelsView(averageEnergy);

        this.lifeSpan = new Label(simulationDailyStatistics.getAverageLifeSpanForDeadAnimals());
        setLabelsView(lifeSpan);

        this.childrenCount = new Label(simulationDailyStatistics.getAverageChildCountForLivingAnimals());
        setLabelsView(childrenCount);

        Label genotypeStatistics = new Label("Genotype statistics: ");
        setLabelsView(genotypeStatistics);

        this.genotypeStatisticsValue = new Label(simulationDailyStatistics.getGenotypeStatisticsValue());
        setLabelsView(genotypeStatisticsValue);

        detailsBox.getChildren().addAll(simulationDay, animalCount, plantCount, averageEnergy, lifeSpan, childrenCount,
                genotypeStatistics, genotypeStatisticsValue);
        detailsBox.setAlignment(Pos.TOP_CENTER);

        ScrollPane scroll = new ScrollPane();
        scroll.setPrefSize(prefWidth, prefHeight * 9 / 10);
        scroll.setContent(detailsBox);

        title.setAlignment(Pos.BASELINE_CENTER);
        getChildren().addAll(title, scroll);
    }

    private void setLabelsView(Label label) {
        label.setFont(new Font("Tahoma", 10));
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);
    }

    public void update() {
        this.simulationDay.setText(simulationDailyStatistics.getSimulationDay());
        this.animalCount.setText(simulationDailyStatistics.getAnimalCount());
        this.plantCount.setText(simulationDailyStatistics.getPlantCount());
        this.averageEnergy.setText(simulationDailyStatistics.getAverageAnimalEnergyForLivingAnimals());
        this.lifeSpan.setText(simulationDailyStatistics.getAverageLifeSpanForDeadAnimals());
        this.childrenCount.setText(simulationDailyStatistics.getAverageChildCountForLivingAnimals());
        this.genotypeStatisticsValue.setText(simulationDailyStatistics.getGenotypeStatisticsValue());
    }
}
