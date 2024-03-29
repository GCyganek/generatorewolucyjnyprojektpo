import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import simulation.container.SimulationsContainer;
import simulation.util.SimulationDayDuration;
import visualisation.MainWindow;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage = new MainWindow();

        Thread thread = new Thread(() -> {
            Runnable runnable = SimulationsContainer::runSimulation;
            while (true) {
                try {
                    Thread.sleep((long) SimulationDayDuration.duration);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
                Platform.runLater(runnable);
            }
        });
        thread.setDaemon(true);
        thread.start();

        stage.show();
    }

}
