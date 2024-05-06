package application;

import javafx.scene.image.Image;

public class Player{
	
	private int score;


	public Player (int score) {
		this.score = score;		
	}
	
	public void addPoints() {
		this.score = this.score+100;
		System.out.println("New Score:" + this.score + "\n");
	}
	
	public void deductPoints() {
		this.score = this.score - 100;
	}
	
	public int getScore() {
		return this.score;
	}
//	
//	public void setX(int x){
//		this.x=x;
//	}
//	
//	
//	public double getX(){
//		return x;
//	}
//	
//	public double getY(){
//		return y;
//	}
//	
//	public void setY(int y){
//		this.y=y;		
//	}
//
//	
    
}