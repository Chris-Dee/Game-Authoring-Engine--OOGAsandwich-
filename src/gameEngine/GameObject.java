package gameEngine;

import jgame.*;

public class GameObject extends JGObject {
	
	private GameObjectAction myMovement;
	private String myFuckingName;
	private double xspeedMultiple;
	private double yspeedMultiple;
	
	public GameObject(String name, JGPoint position, int colid, String sprite, GameObjectAction move, double xspeedMultiple, double yspeedMultiple) {
		super(name, true, position.x, position.y, colid, sprite);
		myMovement = move;
		myFuckingName=name;
		myMovement.addObject(this);
		this.xspeedMultiple = xspeedMultiple;
		this.yspeedMultiple = yspeedMultiple;
	}
	
	public GameObject(String name, JGPoint position, int colid, String sprite, GameObjectAction move) {
		super(name, true, position.x, position.y, colid, sprite);
		myMovement = move;
		myFuckingName=name;
		myMovement.addObject(this);
		this.xspeedMultiple = 1;
		this.yspeedMultiple = 1;
	}

	public void move() {
		if(myMovement.getIsStart()){
			myMovement.start();
		}
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
	
	
	
	public GameObjectAction getMovement(){
		return myMovement;
	}

	public void applyInternalForces() {
		// TODO: eventually make this a linear transformation of stats
		xspeed = xspeed * xspeedMultiple;
		yspeed = yspeed * yspeedMultiple;
	}

}
