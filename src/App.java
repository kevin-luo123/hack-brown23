
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {

    @Override
    public void start(Stage stage) {
        PaneOrganizer paneOrganizer = new PaneOrganizer();
        Scene scene = new Scene(paneOrganizer.getRoot(),Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
        stage.setScene(scene);
        stage.setTitle("Metronome");
        stage.show();
    }

    public static void main(String[] argv) {
        launch(argv);
    }
}
