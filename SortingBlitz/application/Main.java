package application;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX App
 */
public class Main extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        SceneController scene = new SceneController();
        scene.showIntroScreen(stage);

    }

    public static void main(String[] args) {
        launch();
    }

}