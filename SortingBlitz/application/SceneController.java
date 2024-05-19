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

import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;

import javafx.scene.input.KeyCode;

public class SceneController {
    
    private Scene scene;
    private Stage stage;
    public static final int WINDOW_HEIGHT = 1080;
	public static final int WINDOW_WIDTH = 1920;
	
	  // Get screen dimensions to get the full screen
    static Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    static double screenWidth = screenBounds.getWidth();
    static double screenHeight = screenBounds.getHeight();
    

    private TextArea messageInput;
    private TextArea chatDisplay;
    
    private final int PORT = 5050;




	 public void showIntroScreen(Stage stage) throws IOException {
	        this.stage = stage;
	        Group root = new Group();
	        scene = new Scene(root);

	        VBox buttonContainer = new VBox();
	        buttonContainer.setAlignment(Pos.CENTER); // Align the VBox contents to the center
	        
	        HBox hbox = new HBox();
	        
	        Button serverButton = new Button("Start as Server");
	        Button clientButton = new Button("Connect as Client");

	        
	        hbox.getChildren().addAll(serverButton, clientButton);

	        buttonContainer.getChildren().addAll( hbox );

	        root.getChildren().addAll(buttonContainer);
	        
	        serverButton.setOnAction(event -> switchToGameServer(event, root, buttonContainer));
	        clientButton.setOnAction(event -> switchToGameClient(event, root,buttonContainer ));
	        
	        
	        
	        stage.setScene(scene);
	        
	        // Set window dimensions to match screen size
	        stage.setWidth(screenWidth);
	        stage.setHeight(screenHeight);

	        // Set fullscreen
	        stage.setFullScreen(true);
	        stage.show();
	        
	    }
	 

	 
	 public void switchToGameClient(ActionEvent event, Group root_prev, VBox buttonContainer) {

		 	
	        Label nameLabel = new Label("Enter your name:");
	        // Create text field
	        TextField nameTextField = new TextField();
	        
	        Label ipLabel = new Label("Enter the Ip you want to connect:");
	        // Create text field
	        TextField ipTextField = new TextField();
	        
	        Button gameButton = new Button("Start as Game");
	        
	        gameButton.setOnAction(gameevent -> switchToGame(gameevent,nameTextField.getText() , Integer.parseInt(ipTextField.getText())));
	        
	        
	        buttonContainer.getChildren().addAll( nameLabel, nameTextField,  ipLabel, ipTextField, gameButton);

	        root_prev.getChildren().clear(); // Clear previous children
	        root_prev.getChildren().add(buttonContainer);


		}
	 
	 
	 public void switchToGameServer(ActionEvent event, Group root_prev, VBox buttonContainer) {	  
	        Label nameLabel = new Label("Enter your name:");
	        // Create text field
	        TextField nameTextField = new TextField();
	        
	        Button gameButton = new Button("Start as Game");
	        
	        gameButton.setOnAction(gameevent -> {
	            final String playerName = nameTextField.getText();
	            
	            Thread serverThread = new Thread(() -> {
	                try {
	                    System.out.println(playerName);
	                    Server server = new Server(playerName, PORT);
	                } catch(IOException e) {
	                    System.out.println(e);
	                }
	            });
	            
	            serverThread.start(); // Start the server thread
	            
	            switchToGame(gameevent, playerName, PORT);
	        });
	        
	        buttonContainer.getChildren().addAll( nameLabel, nameTextField,  gameButton);


	        root_prev.getChildren().clear(); // Clear previous children
	        root_prev.getChildren().add(buttonContainer);
		}
	 
	 
	 private void switchToGame(ActionEvent event, String name, int port) {
		    Group root = new Group();        
		    Canvas canvas = new Canvas(SceneController.screenWidth, SceneController.screenHeight);    
		    GraphicsContext gc = canvas.getGraphicsContext2D();
		    		  
		    Image backgroundImage = new Image("assets/asset_bg.png");
		    Image chatBgImage = new Image("assets/chatBg.png");
		    		   
		    Pane mainPane = new Pane();
		    mainPane.setPrefSize(SceneController.screenWidth, SceneController.screenHeight);
		    		   
		    Rectangle rectangle = new Rectangle(0, 0, SceneController.screenWidth * 0.8, SceneController.screenHeight);
		    rectangle.setFill(new ImagePattern(backgroundImage)); 
		    mainPane.getChildren().add(rectangle); 
		    
		    Image borderImage = new Image("assets/border.png");
		    Rectangle rectangleBorder = new Rectangle(0, 0, SceneController.screenWidth * 0.8, SceneController.screenHeight);
		    rectangleBorder.setFill(new ImagePattern(borderImage)); 
		    mainPane.getChildren().add(rectangleBorder); 
		    

		    double sideWidth = SceneController.screenWidth * 0.2;
		    Rectangle sideRectangle = new Rectangle(SceneController.screenWidth * 0.8, 0, sideWidth, SceneController.screenHeight);
		    sideRectangle.setFill(new ImagePattern(chatBgImage)); 
		    mainPane.getChildren().add(sideRectangle); 
//		    
	        // Initialize UI components
		    VBox chatContainer = new VBox();
		    chatDisplay = new TextArea();
		    messageInput = new TextArea();
		    
		    chatDisplay.setWrapText(true);
		    messageInput.setWrapText(true);
		    Button sendBtn = new Button("Send");
		    
		   

		    String placeholder = "Enter your message here...";
	        messageInput.setText(placeholder);
	        messageInput.setStyle("-fx-text-fill: gray;");

	        messageInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
	            if (newValue) {
	                if (messageInput.getText().equals(placeholder)) {
	                    messageInput.setText("");
	                    messageInput.setStyle("-fx-text-fill: black;");
	                }
	            } else {
	                if (messageInput.getText().isEmpty()) {
	                    messageInput.setText(placeholder);
	                    messageInput.setStyle("-fx-text-fill: gray;");
	                }
	            }
	        });

	        
		    double padding = 70;
		    double chatContainerWidth = sideWidth- padding;
		    double chatContainerHeight = SceneController.screenHeight - padding;		   		   
		    
		    
		    double sendBtnWidth = chatContainerWidth;
		    double sendBtnHeight = 30;
		    
		    double messageInputWidth = chatContainerWidth ;
		    double messageInputHeight = 75;
		    
		    double chatDisplayWidth = chatContainerWidth  ;
		    double chatDisplayHeight = chatContainerHeight -messageInputHeight - sendBtnHeight - padding;
		    
	        // Set properties
	        chatDisplay.setEditable(false);
	        chatDisplay.setPrefWidth(chatDisplayWidth); // Set the preferred width
	        chatDisplay.setPrefHeight(chatDisplayHeight );
	        VBox.setMargin(chatDisplay, new Insets(10, 0, 0, 0)); 
	        
	        messageInput.setPrefWidth(messageInputWidth);
	        messageInput.setPrefHeight(messageInputHeight);
	        VBox.setMargin(messageInput, new Insets(10, 0, 0, 0)); 
	        
	        sendBtn.setPrefWidth(sendBtnWidth);
	        sendBtn.setPrefHeight(sendBtnHeight);
	        VBox.setMargin(sendBtn, new Insets(10, 0, 0, 0)); 
	        
	        chatContainer.getChildren().addAll(chatDisplay, messageInput,sendBtn);

	        
	        try {
	        	Client client= new Client(name, port, chatDisplay); 
	        	
	        	
	        	messageInput.setOnKeyPressed(e -> {
	                if (e.getCode() == KeyCode.ENTER && !messageInput.getText().trim().isEmpty()) {
	                    System.out.println(messageInput.getText());
	                    client.sendMessage(messageInput.getText().trim());
	                    messageInput.clear();
	                }
	            });

	            sendBtn.setOnMouseClicked(e -> {
	                if (!messageInput.getText().trim().isEmpty()) {
	                    System.out.println(messageInput.getText());
	                    client.sendMessage(messageInput.getText().trim());
	                    messageInput.clear();
	                }
	            });
	        }catch(IOException e) {
	        	System.out.println(e);
	        	System.exit(0);
	        }
	        
	        // Event handlers
	        
	        
	        chatContainer.setLayoutX(SceneController.screenWidth * 0.8+ (padding/2)); // Set X position
	        chatContainer.setLayoutY(padding/2); 
//	        chatContainer.setPrefWidth(chatContainerWidth); // Set the preferred width
//	        chatContainer.setPrefHeight(chatContainerHeight);//		   
		    root.getChildren().addAll(mainPane, canvas, chatContainer);
		    
		    
		    
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