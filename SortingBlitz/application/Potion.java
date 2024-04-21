package application;

import javafx.scene.image.Image;

public class Potion extends sprite{

//	public final static Image IMAGE= new Image(Blobs.class.getResourceAsStream("/assets/potions/pots_1.png"),150, 150,false,false);
	
	private boolean visible;
	private boolean isBomb;
	private int index;

	Potion(int x, int y,boolean isBomb, Image img){
		super(x,y);
		this.loadImage(img);
		this.visible=true;
		this.isBomb = isBomb;
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
