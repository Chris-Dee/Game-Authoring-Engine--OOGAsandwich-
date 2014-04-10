package gameEngine;

import java.util.Map;

import jgame.*;

public class GameObject extends JGObject {
	
	private GameObjectAction myMovement;
	private String myFuckingName;
	private boolean isFloating;
	private boolean isScreenFollow;
	private Map<Integer, String> charMap;
	public JGPoint originalPosition;
//	private double xspeedMultiple;
//	private double yspeedMultiple;
	
	public GameObject(String name, JGPoint position, int colid, String sprite, String behavior, int time, int speed, boolean floating) {
		super(name, true, position.x, position.y, colid, sprite);
		myMovement = new GameObjectAction(behavior, time, speed);
		myFuckingName=name;
//		myMovement.addObject(this);
		isFloating = floating;
		isScreenFollow = false;
		originalPosition = new JGPoint(position.x, position.y);
//		this.xspeedMultiple = xspeedMultiple;
//		this.yspeedMultiple = yspeedMultiple;
	}
	public void reset(){
		x = originalPosition.x;
		y = originalPosition.y;
	}
	
//	public GameObject(String name, JGPoint position, int colid, String sprite, int xspeed, int yspeed, boolean floating) {
//		super(name, true, position.x, position.y, colid, sprite);
//		myMovement = new GameObjectAction(xspeed, yspeed);
//		myFuckingName=name;
////		myMovement.addObject(this);
//		isFloating = floating;
////		this.xspeedMultiple = xspeedMultiple;
////		this.yspeedMultiple = yspeedMultiple;
//	}
	
	public GameObject(String name, JGPoint position, int colid, String sprite, Map<Integer, String> inputMap, boolean floating, boolean screenFollow) {
		super(name, true, position.x, position.y, colid, sprite);
		myMovement = new GameObjectAction(inputMap);
		myFuckingName=name;
//		myMovement.addObject(this);
		isFloating = floating;
		isScreenFollow = screenFollow;
//		this.xspeedMultiple = xspeedMultiple;
//		this.yspeedMultiple = yspeedMultiple;
		charMap=inputMap;
		originalPosition = new JGPoint(position.x, position.y);
	}
	
	public GameObject(String name, JGPoint position, int colid, String sprite, Map<Integer, String> inputMap, boolean floating) {
		super(name, true, position.x, position.y, colid, sprite);
		myMovement = new GameObjectAction(inputMap);
		myFuckingName=name;
//		myMovement.addObject(this);
		isFloating = floating;
		isScreenFollow = false;
//		this.xspeedMultiple = xspeedMultiple;
//		this.yspeedMultiple = yspeedMultiple;
		charMap=inputMap;
		originalPosition = new JGPoint(position.x, position.y);
	}
	
	public GameObject(String name, JGPoint position, int colid, String sprite, boolean floating) {
		super(name, true, position.x, position.y, colid, sprite);
		myMovement = new GameObjectAction();
		myFuckingName=name;
//		myMovement.addObject(this);
		isFloating = floating;
		isScreenFollow = false;
//		this.xspeedMultiple = xspeedMultiple;
//		this.yspeedMultiple = yspeedMultiple;
		originalPosition = new JGPoint(position.x, position.y);
	}
	
//	public GameObject(String name, JGPoint position, int colid, String sprite, GameObjectAction move) {
//		super(name, true, position.x, position.y, colid, sprite);
//		myMovement = move;
//		myFuckingName=name;
//		myMovement.addObject(this);
//		this.xspeedMultiple = 1;
//		this.yspeedMultiple = 1;
//	}

	public void move() {
		if(myMovement.getIsStart()){
			myMovement.start(this);
		}
		//System.out.println("move");
	}
	
//	public void pace(int time){
//		xspeed=2;
//		new JGTimer(time,false) {
//			public void alarm() {
//				xspeed=-xspeed;
//			}
//		};
//	}
	
	public String getFuckingName() {
		return myFuckingName;
	}
	
	public boolean getIsFloating(){
		return isFloating;
	}
	
	public Map<Integer, String> getCharMap(){
		return charMap;
	}
	
	public GameObjectAction getMovement(){
		return myMovement;
	}
	
	public boolean getIsScreenFollowing(){
		return isScreenFollow;
	}

//	public void applyInternalForces() {
//		// TODO: eventually make this a linear transformation of stats
//		xspeed = xspeed * xspeedMultiple;
//		yspeed = yspeed * yspeedMultiple;
//	}

}
