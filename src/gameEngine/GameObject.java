package gameEngine;

import jgame.*;

public class GameObject extends JGObject {
	
	public Movement myMovement;
	private String myFuckingName;
	
	public GameObject(String name, JGPoint position, int colid, String sprite, Movement move) {
		super(name, true, position.x, position.y, colid, sprite);
		myMovement = move;
		myFuckingName=name;
	}
	
	public void move() {
		if(myMovement.getIsStart()){
			myMovement.start();
		}
		this.xspeed = myMovement.getXSpeed();
		this.yspeed = myMovement.getYSpeed();
	}
	
	public void pace(int time){
		xspeed=2;
		new JGTimer(time,false) {
			public void alarm() {
				xspeed=-xspeed;
			}
		};
	}
	
	public String getFuckingName() {
		return myFuckingName;
	}
	
	public void moveRight(){
		x+=2;
	}
	
	public void moveLeft(){
		x-=2;
	}
	
	public void moveUp(){
		y-=2;
	}
	
	public void moveDown(){
		y+=2;
	}
	public void stopMovement(){
		xspeed=0;
		yspeed=0;
	}

}
