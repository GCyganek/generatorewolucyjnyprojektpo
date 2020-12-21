package Visualisation.Settings;

import Simulation.SimulationDayDuration;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

public class IntervalSlider extends HBox {

    public IntervalSlider(double prefWidth, double prefHeight) {
        setPrefSize(prefWidth, prefHeight);
        setSpacing(20);

        Button durationValue = new Button("Current day duration: " + SimulationDayDuration.duration + " ms");

        Slider slider = new Slider(10, 2000, 500);

        slider.setShowTickLabels(true);
        slider.setPrefWidth(prefWidth / 3);
        slider.setShowTickMarks(true);

        Button button = new Button("Set day duration [ms]");

        button.setOnAction(event -> {
            SimulationDayDuration.setDuration(slider.getValue());
            durationValue.setText("Current day duration: " + (long) slider.getValue() + " ms");
        });

        getChildren().addAll(durationValue, slider, button);
    }
}
