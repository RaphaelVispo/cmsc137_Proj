package application;

import javafx.scene.image.Image;

public class Potion extends sprite{

	
	private boolean visible;
	private boolean isBomb;
	private int index;
	private String color;

	Potion(int x, int y,boolean isBomb, Image img, String color){
		super(x,y);
		this.loadImage(img);
		this.visible=true;
		this.isBomb = isBomb;
		this.color = color;
	}

	public boolean contains(double x, double y){
		return this.getBounds().contains(x, y);
	}

	public void setVisible(boolean value){
		this.visible = value;
	}

	public boolean getVisible(){
		return visible;
	}
	
	public String getPotionColor() {
		return this.color;
	}
	
	
	public void setNewState(int x, int y, Image img, String color) {
		this.x = x;
		this.y = y;
		this.loadImage(img);
		this.color = color;
	}
	
	public boolean getIfBomb() {
		return this.isBomb;
	}



}
