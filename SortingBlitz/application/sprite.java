package application;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class sprite {
    protected Image img;
	protected double x, y, dx, dy;
    protected double width;
	protected double height;

    public sprite(int xPos, int yPos){
		this.x = xPos;
		this.y = yPos;
	}
    protected void loadImage(Image img){
		try{
			this.img = img;
	        this.setSize();
		} catch(Exception e){}
	}
	
	//method to set the image to the image view node
	void render(GraphicsContext gc){
		gc.drawImage(this.img, this.x, this.y);
        
    }
	
	//method to set the object's width and height properties
	private void setSize(){
		this.width = this.img.getWidth();
	    this.height = this.img.getHeight();
	}

	public double getX() {
    	return this.x;
	}

	public double getY() {
    	return this.y;
	}

	public void setX(double x) {
    	this.x = x;
	}

	public void setY(double y) {
    	this.y = y;
	}

	public Rectangle2D getBounds(){
		return new Rectangle2D(this.x, this.y, this.width, this.height);
	}
	

}
