package application;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;


public class Cauldron extends sprite {
    
//	public final static Image IMAGE= new Image("couldron.png",100, 100,false,false);
	public final static Image IMAGE = new Image("/assets/couldron.png", 100, 100, false, false);
	int color;

	Cauldron(int x, int y, Image img, int color){
		super(x,y);
		this.loadImage(img);
		this.color = color;
	



		
	}


	//method that will check for collision of two sprites
	public boolean collidesWith(sprite rect2)	{
		Rectangle2D rectangle1 = this.getBounds();
		Rectangle2D rectangle2 = rect2.getBounds();

		return rectangle1.intersects(rectangle2);
	}
}
