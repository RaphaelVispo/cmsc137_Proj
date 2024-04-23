package application;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	        Canvas canvas = new Canvas(SceneController.WINDOW_WIDTH, SceneController.WINDOW_HEIGHT);    
		    GraphicsContext gc = canvas.getGraphicsContext2D();
	        
	        Image backgroundImage = new Image("assets/bg_whole.png");
	        
	        Pane mainPane = new Pane();
		    mainPane.setPrefSize(SceneController.screenWidth, SceneController.screenHeight);
		    
 		   
		    Rectangle rectangle = new Rectangle(0, 0, SceneController.screenWidth, SceneController.screenHeight);
		    rectangle.setFill(new ImagePattern(backgroundImage)); 
		    mainPane.getChildren().add(rectangle); 
		    
		    root.getChildren().addAll(mainPane, canvas);
		    
	        VBox buttonContainer = new VBox();
	        buttonContainer.setAlignment(Pos.CENTER); // Align the VBox contents to the center
	        buttonContainer.setPrefSize(SceneController.screenWidth, SceneController.screenHeight);
	        
	        
	        
//	        START BUTTON
	        Image buttonImage1 = new Image("assets/buttons/B_Start1.png");
	        Image buttonImage2 = new Image("assets/buttons/B_Start2.png");
	        Image buttonImage3 = new Image("assets/buttons/B_Start3.png");
	        ImageView imageView = new ImageView(buttonImage1);
	        imageView.setFitWidth(120); // Set width of the image
	        imageView.setFitHeight(120); // Set height of the image
	        imageView.setPreserveRatio(true); 
	        
	        Button strBtn = new Button();
	        strBtn.setGraphic(imageView);
	        

	        strBtn.setStyle("-fx-background-color: transparent; -fx-background-insets: 0;");
	        strBtn.setPrefWidth(120); 
	        strBtn.setPrefHeight(120);
	        strBtn.setOnAction(event -> switchToGame(event));
	        
	        // Add hover effect
	        strBtn.setOnMouseEntered(e -> {
	        	imageView.setImage(buttonImage3);	        		        
	        });

	        strBtn.setOnMouseExited(e -> {
	        	imageView.setImage(buttonImage1);
	        });
	        
	        buttonContainer.getChildren().addAll(strBtn);
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
		    Pane root = new Pane(); // Create a new Pane instance
		    scene.setRoot(root); // Set the new Pane as the root of the scene
		    
		    // Create game content
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
		    
		    // Set the same scene with updated content
		    stage.setScene(scene);
		    stage.setWidth(SceneController.WINDOW_WIDTH);
		    stage.setHeight(SceneController.WINDOW_HEIGHT);
		    stage.setFullScreen(true);
		    
		    // Start the game timer
		    GameTimer gametimer = new GameTimer(gc, scene);
		    gametimer.start();
		}



	
}