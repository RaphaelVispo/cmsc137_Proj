package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class GameTimer extends AnimationTimer {
	private GraphicsContext gc;
	public static final int WINDOW_HEIGHT = 1080;
	public static final int WINDOW_WIDTH = 1920;

  
	private Scene scene;
	private Cauldron playerCauldron;
	  
	private ArrayList<Cauldron> cauldronList = new ArrayList<Cauldron>();
	private ArrayList<Potion> potionList = new ArrayList<Potion>();
	

	
	private ArrayList<Image> cauldronImgs = new ArrayList<Image>();
	private ArrayList<Image> potionImgs = new ArrayList<Image>();
	private boolean isDragging = false;
	private double dragStartX;
	private double dragStartY;
	
	
	Map<String, Double> cauldronSizes = new HashMap<>();
	Map<String, Double> potionSizes = new HashMap<>();
	int numOfPotion = 8;

    GameTimer(GraphicsContext gc, Scene theScene){
    	
    	cauldronSizes.put("width",(double) 150 );
    	cauldronSizes.put("height",(double) 150 );
    	
    	
    	potionSizes.put("width",(double) 100 );
    	potionSizes.put("height",(double) 100 );
    	
		this.gc = gc;
        this.scene = theScene;
        
        this.loadImg();
        this.generateCauldron();
        this.generatePotion();
        
	}


    
    @Override
	public void handle(long currentNanoTime) {

        gc.clearRect(0, 0, GameTimer.WINDOW_HEIGHT, GameTimer.WINDOW_WIDTH);

 
        renderPotion();
        renderCauldron();
        
        for (int i=0; i<4; i++) {
          cauldronList.get(i).render(gc);
        }
        
        
        
        
        
        for(Potion potion: potionList) {
        	this.checkDrag(potion);
        }
        
        


    }
    
    
    public void generatePotion() {
    	int marginTop = 30;
    	int marginLeftRight = 30;
    	
    	int minX = marginLeftRight;
    	int maxX = (int) (SceneController.screenWidth * 0.8 - cauldronSizes.get("width") -potionSizes.get("height") -  marginLeftRight ) ; ;

    	int minY = marginTop;
    	int maxY = (int) (SceneController.screenHeight - cauldronSizes.get("height") - potionSizes.get("height")) ;
    	
    	while (potionList.size() != numOfPotion) {
    		
    		int posX = (int)(Math.random() * (maxX - minX + 1)) + minX;
    		int posY = (int)(Math.random() * (maxY - minY + 1)) + minY;
    		int color = (int) (Math.random() * (4 - 1 + 1)) + 1;
    		
   			
			potionList.add(new Potion(posX, posY, false, potionImgs.get(color)));
    		
    	}
    	
    }
    public void generateCauldron() {
    	int margin = 200;
    	int cauldronSize = 150;
    	double space = (int) (((SceneController.screenWidth * 0.8) - (margin *2) - (4 * cauldronSize)))/4;
    	

    	for (int i = 0;  i<4; i++) {
    		int x = (int) (margin + (i * (cauldronSize + space)));
    		int y =  (int) SceneController.screenHeight - cauldronSize;
    		cauldronList.add(new Cauldron(x, y, cauldronImgs.get(i), i+1));
    	};
    	
    	playerCauldron = cauldronList.get(2);
    	
    }	
    
    public void loadImg() {
    	double cauldron_width = cauldronSizes.get("width");
    	double cauldron_height = cauldronSizes.get("height");
    	cauldronImgs.add(new Image("/assets/cauldron_scarlet.png", cauldron_width, cauldron_height, false, true));
    	cauldronImgs.add(new Image("/assets/cauldron_yellow.png", cauldron_width, cauldron_height, false, false));
    	cauldronImgs.add(new Image("/assets/cauldron_violet.png", cauldron_width, cauldron_height, false, false));
    	cauldronImgs.add(new Image("/assets/cauldron_aqua.png", cauldron_width, cauldron_height, false, false));
    	
    	
    	double potion_width = potionSizes.get("width");
    	double potion_height = potionSizes.get("height");
    	potionImgs.add(new Image("/assets/potions/pots_0.png", potion_width, potion_height, false, true));
    	potionImgs.add(new Image("/assets/potions/pots_1.png", potion_width, potion_height, false, false));
    	potionImgs.add(new Image("/assets/potions/pots_2.png", potion_width, potion_height, false, false));
    	potionImgs.add(new Image("/assets/potions/pots_3.png", potion_width, potion_height, false, false));
    	potionImgs.add(new Image("/assets/potions/pots_4.png", potion_width, potion_height, false, false));

    }


    
    private void renderPotion(){
    	
    	for (Potion potion : potionList) {            
            if (potion.getVisible()){
            	potion.render(gc);
            }
        }
    }
    
    private void renderCauldron(){
    	
		for (int i=0; i<4; i++) {
		       cauldronList.get(i).render(gc);
		}
    }
    private void checkDrag(Potion potion){
        scene.setOnMousePressed(event -> {
            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();
            
            // Check if the mouse press is inside the blob
            if (potion.contains(mouseX, mouseY)) {
                isDragging = true;
                dragStartX = mouseX - potion.getX();
                dragStartY = mouseY - potion.getY();              
                
            }
        });

        // Event handler for mouse drag
        scene.setOnMouseDragged(event -> {
            if (isDragging) {
                double newX = event.getSceneX() - dragStartX;
                double newY = event.getSceneY() - dragStartY;

                potion.setX(newX);
                potion.setY(newY);

                if (playerCauldron.collidesWith(potion)){
                	potion.setVisibleFalse();
                	
                }
            }
        });

        // Event handler for mouse release
        scene.setOnMouseReleased(event -> {
            isDragging = false;
        });
    }
}

