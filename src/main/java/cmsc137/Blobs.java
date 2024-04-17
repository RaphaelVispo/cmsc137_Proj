package cmsc137;

import javafx.scene.image.Image;

public class Blobs extends sprite {
    
	public final static Image IMAGE= new Image("blob.png",150, 150,false,false);
	
	private boolean visible;

	Blobs(int x, int y){
		super(x,y);
		this.loadImage(Blobs.IMAGE);
		this.visible=true;
	}

	public boolean contains(double x, double y){
		return this.getBounds().contains(x, y);
	}

	public void setVisibleFalse(){
		this.visible = false;
	}

	public boolean getVisible(){
		return visible;
	}
}
