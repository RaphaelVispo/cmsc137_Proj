package cmsc137;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javafx.event.ActionEvent;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class SceneController {
    
    private Scene scene;
    private Stage stage;
    public static final int WINDOW_HEIGHT = 500;
	public static final int WINDOW_WIDTH = 800;

    public void showIntroScreen(Stage stage) throws IOException{
        this.stage = stage;
        Group root = new Group();
        scene = new Scene(root, SceneController.WINDOW_WIDTH, SceneController.WINDOW_HEIGHT);
        VBox buttonContainer = new VBox();

        Button button1 = new Button("Button 1");
        button1.setOnAction(event -> switchToGame(event));

        buttonContainer.getChildren().addAll(button1);

        root.getChildren().add(buttonContainer);
        stage.setScene(scene);
        stage.show();
        
    }

    public void switchToGame(ActionEvent event) {
        Group root = new Group();		
		Canvas canvas = new Canvas(SceneController.WINDOW_WIDTH,SceneController.WINDOW_HEIGHT);	
		GraphicsContext gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		Scene scene2 = new Scene(root, SceneController.WINDOW_WIDTH,SceneController.WINDOW_HEIGHT);
		GameTimer gametimer = new GameTimer(gc,scene2);
        stage.setScene(scene2);
        stage.show();
        gametimer.start();
        // Set the new scene to the stage
        // stage.setScene(newScene);
    }

}
