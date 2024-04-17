package cmsc137;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

public class GameTimer extends AnimationTimer {
	private GraphicsContext gc;
    public static final int WINDOW_HEIGHT = 500;
	public static final int WINDOW_WIDTH = 800;
    
    private Scene scene;
    private Blobs blob;
    private Couldron couldron;

    private boolean isDragging = false;
    private double dragStartX;
    private double dragStartY;

    GameTimer(GraphicsContext gc, Scene theScene){
		this.gc = gc;
        this.scene = theScene;

        couldron = new Couldron(400, 400);
        blob = new Blobs(200, 200);
	}


    @Override
	public void handle(long currentNanoTime) {

        gc.clearRect(0, 0, GameTimer.WINDOW_HEIGHT, GameTimer.WINDOW_WIDTH);

        this.checkDrag();
        renderBlob();
        couldron.render(gc);


    }

    private void renderBlob(){
        if (blob.getVisible()){
            blob.render(gc);
        }
    }

    private void checkDrag(){
        scene.setOnMousePressed(event -> {
            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();
            
            // Check if the mouse press is inside the blob
            if (blob.contains(mouseX, mouseY)) {
                isDragging = true;
                dragStartX = mouseX - blob.getX();
                dragStartY = mouseY - blob.getY();
            }
        });

        // Event handler for mouse drag
        scene.setOnMouseDragged(event -> {
            if (isDragging) {
                double newX = event.getSceneX() - dragStartX;
                double newY = event.getSceneY() - dragStartY;

                blob.setX(newX);
                blob.setY(newY);

                if (couldron.collidesWith(blob)){
                    blob.setVisibleFalse();
                }
            }
        });

        // Event handler for mouse release
        scene.setOnMouseReleased(event -> {
            isDragging = false;
        });
    }
}
