package application;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;





public class SceneController {
    
    private Scene scene;
    private Stage stage;
    public static final int WINDOW_HEIGHT = 1080;
	public static final int WINDOW_WIDTH = 1920;
	
	  // Get screen dimensions to get the full screen
    static Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    static double screenWidth = screenBounds.getWidth();
    static double screenHeight = screenBounds.getHeight();

	 public void showIntroScreen(Stage stage) throws IOException {
	        this.stage = stage;
	        Group root = new Group();
	        scene = new Scene(root);

	        VBox buttonContainer = new VBox();
	        buttonContainer.setAlignment(Pos.CENTER); // Align the VBox contents to the center
	        
	        Button button1 = new Button("Button 1");
	        button1.setOnAction(event -> switchToGame(event));

	        buttonContainer.getChildren().addAll(button1);

	        root.getChildren().add(buttonContainer);
	        stage.setScene(scene);
	        
	        // Set window dimensions to match screen size
	        stage.setWidth(screenWidth);
	        stage.setHeight(screenHeight);

	        // Set fullscreen
	        stage.setFullScreen(true);
	        stage.show();
	        
	    }
	 
	 public void switchToGame(ActionEvent event) {
		    Group root = new Group();        
		    Canvas canvas = new Canvas(SceneController.WINDOW_WIDTH, SceneController.WINDOW_HEIGHT);    
		    GraphicsContext gc = canvas.getGraphicsContext2D();
		    		  
		    Image backgroundImage = new Image("assets/asset_bg.png");
		    		   
		    Pane mainPane = new Pane();
		    mainPane.setPrefSize(SceneController.screenWidth, SceneController.screenHeight);
		    		   
		    Rectangle rectangle = new Rectangle(0, 0, SceneController.screenWidth * 0.8, SceneController.screenHeight);
		    rectangle.setFill(new ImagePattern(backgroundImage)); 
		    mainPane.getChildren().add(rectangle); 
		    

		    double sideWidth = SceneController.screenWidth * 0.2;
		    Rectangle sideRectangle = new Rectangle(SceneController.screenWidth * 0.8, 0, sideWidth, SceneController.screenHeight);
		    sideRectangle.setFill(Color.INDIGO); 
		    mainPane.getChildren().add(sideRectangle); 
		    
		   
		   
		    root.getChildren().addAll(mainPane, canvas);
		    
		    Scene scene2 = new Scene(root, SceneController.WINDOW_WIDTH, SceneController.WINDOW_HEIGHT);
		    GameTimer gametimer = new GameTimer(gc, scene2);
		    stage.setScene(scene2);
		    stage.setWidth(SceneController.WINDOW_WIDTH);
		    stage.setHeight(SceneController.WINDOW_HEIGHT);
		    stage.setFullScreen(true);
		    stage.show();
		    gametimer.start();
		}

	
}