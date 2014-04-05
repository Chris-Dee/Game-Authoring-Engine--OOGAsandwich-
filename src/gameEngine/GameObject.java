package gameEngine;

import jgame.*;

public class GameObject extends JGObject {
	
	public Movement myMovement;
	
	public GameObject(String name, JGPoint position, Integer colid, String sprite, Movement move) {
		super(name, true, position.x, position.y, colid, sprite);
		myMovement = move;
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

}
