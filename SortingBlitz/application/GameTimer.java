package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;


public class GameTimer extends AnimationTimer {
	private GraphicsContext gc;
	public static final int WINDOW_HEIGHT = 1080;
	public static final int WINDOW_WIDTH = 1920;

  
	private Scene scene;
	private Cauldron playerCauldron;
	  
	private ArrayList<Cauldron> cauldronList = new ArrayList<Cauldron>();
	private ArrayList<Potion> potionList = new ArrayList<Potion>();
	
	

	

	Map <String, Image> cauldronImg = new HashMap<String, Image>();
	Map <String, Image> potionImg = new HashMap<String, Image>();
	List<String> colorsList;
	
	private boolean isDragging = false;
	private double dragStartX;
	private double dragStartY;
	
	private Potion draggedPotion = null; // Track the potion being dragged
	
	
	private int score = 0;
	private Potion potionCollided;
	
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
        
        // Register event handlers
        theScene.setOnMousePressed(this::onMousePressed);
        theScene.setOnMouseDragged(this::onMouseDragged);
        theScene.setOnMouseReleased(this::onMouseReleased);
        
        
        
	}


    
    @Override
	public void handle(long currentNanoTime) {

        gc.clearRect(0, 0, GameTimer.WINDOW_WIDTH, GameTimer.WINDOW_HEIGHT);

 
        renderCauldron();
        renderPotion();
        
        
        checkPotionCauldronCollision(); 
    }
    

    
    public void generatePotion() {
    	int marginTop = 30;
    	int marginLeftRight = 30;
    	
    	int minX = marginLeftRight;
    	int maxX = (int) (SceneController.screenWidth * 0.8 - cauldronSizes.get("width") -potionSizes.get("height") -  marginLeftRight ) ; ;

    	int minY = marginTop;
    	int maxY = (int) (SceneController.screenHeight - cauldronSizes.get("height") - potionSizes.get("height")) ;
    	
    	String color;
    	while (potionList.size() != numOfPotion) {
    		
    		int posX = (int)(Math.random() * (maxX - minX + 1)) + minX;
    		int posY = (int)(Math.random() * (maxY - minY + 1)) + minY;
    		int colorInd = (int) (Math.random() * this.colorsList.size() );
    		
    		color = colorsList.get(colorInd);

			potionList.add(new Potion(posX, posY, false, potionImg.get(color), color));
    	}
    	
    }
    public void generateCauldron() {
    	int margin = 200;
    	int cauldronSize = 150;
    	double space = (int) (((SceneController.screenWidth * 0.8) - (margin *2) - (4 * cauldronSize)))/4;
    	


    	for (int i = 0;  i<4; i++) {
    		int x = (int) (margin + (i * (cauldronSize + space)));
    		int y =  (int) SceneController.screenHeight - cauldronSize;
		
    		String color = colorsList.get(i);
    		cauldronList.add(new Cauldron(x, y, cauldronImg.get(color), color));
    	};
    	

    	
    }	
    
    public void loadImg() {
    	double cauldron_width = cauldronSizes.get("width");
    	double cauldron_height = cauldronSizes.get("height");

    	
    	double potion_width = potionSizes.get("width");
    	double potion_height = potionSizes.get("height");

    	cauldronImg.put("scarlet", new Image("/assets/cauldron_scarlet.png", cauldron_width, cauldron_height, false, true));
    	cauldronImg.put("yellow", new Image("/assets/cauldron_yellow.png", cauldron_width, cauldron_height, false, false));
    	cauldronImg.put("violet", new Image("/assets/cauldron_violet.png", cauldron_width, cauldron_height, false, false));
    	cauldronImg.put("aqua", new Image("/assets/cauldron_aqua.png", cauldron_width, cauldron_height, false, false));

    	potionImg.put("black", new Image("/assets/potions/pots_0.png", potion_width, potion_height, false, true));
    	potionImg.put("aqua", new Image("/assets/potions/pots_1.png", potion_width, potion_height, false, false));
    	potionImg.put("violet", new Image("/assets/potions/pots_2.png", potion_width, potion_height, false, false));
    	potionImg.put("scarlet", new Image("/assets/potions/pots_3.png", potion_width, potion_height, false, false));
    	potionImg.put("yellow", new Image("/assets/potions/pots_4.png", potion_width, potion_height, false, false));
    	
    	this.colorsList = new ArrayList<>(cauldronImg.keySet());
    	
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

    private void onMousePressed(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();

        for (Potion potion : potionList) {
            if (potion.contains(mouseX, mouseY)) {
                isDragging = true;
                draggedPotion = potion;
                dragStartX = potion.getX() - mouseX;
                dragStartY = potion.getY() - mouseY;
                break;
            }
        }
    }

    private void onMouseDragged(MouseEvent event) {
        if (isDragging && draggedPotion != null) {
            draggedPotion.setX(event.getX() + dragStartX);
            draggedPotion.setY(event.getY() + dragStartY);
        }
    }

    private void onMouseReleased(MouseEvent event) {
        isDragging = false;
        draggedPotion = null;
    }
    
    private void checkPotionCauldronCollision() {
        Iterator<Potion> iterator = potionList.iterator();
        List<Potion> potionsToAdd = new ArrayList<>();
        while (iterator.hasNext()) {
            Potion potion = iterator.next();
            for (Cauldron cauldron : cauldronList) {
                if (cauldron.collidesWith(potion)) {                   
                    potion.setVisible(false);
                    iterator.remove(); // Use iterator's remove() method to safely remove the potion (used to avoid error)
                    generateNewPotion(potionsToAdd);
                    if (potion.getPotionColor().equals(cauldron.getCauldronColor())) {
                        this.score += 100;
                    }
                    break; 
                }
            }
        }
        potionList.addAll(potionsToAdd); // Add the collected potions to the potionList
    }

    private void generateNewPotion(List<Potion> potionsToAdd) {
        int marginTop = 30;
        int marginLeftRight = 30;
        
        int minX = marginLeftRight;
        int maxX = (int) (SceneController.screenWidth * 0.8 - cauldronSizes.get("width") - potionSizes.get("height") - marginLeftRight); 
        int minY = marginTop;
        int maxY = (int) (SceneController.screenHeight - cauldronSizes.get("height") - potionSizes.get("height"));
        
        String color;
        int posX = (int) (Math.random() * (maxX - minX + 1)) + minX;
        int posY = (int) (Math.random() * (maxY - minY + 1)) + minY;
        int colorInd = (int) (Math.random() * this.colorsList.size());
        
        color = colorsList.get(colorInd);
        
        potionsToAdd.add(new Potion(posX, posY, false, potionImg.get(color), color));
    }

}

