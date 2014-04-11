package gameEngine;

import gameplayer.Tuple;

import java.util.Map;

import jgame.*;

public class GameObject extends JGObject {

	private GameObjectAction myMovement;
	private String myFuckingName;
	private boolean isFloating;
	private boolean isScreenFollow;
	private Map<Integer, Tuple<String,Integer>> charMap;
	public JGPoint originalPosition;
	private int myID;
	//private int myHitPoints;


	public GameObject(String name, JGPoint position, int colid, String sprite, String behavior, int time, int speed, boolean floating, int id) {
		super(name, true, position.x, position.y, colid, sprite);
		myMovement = new GameObjectAction(behavior, time, speed);
		myFuckingName=name;
		isFloating = floating;
		isScreenFollow = false;
		originalPosition = new JGPoint(position.x, position.y);
		myID = id;
		//myHitPoints=hitPoints;
	}
	public void reset(){
		x = originalPosition.x;
		y = originalPosition.y;
	}

	public GameObject(String name, JGPoint position, int colid, String sprite, Map<Integer, Tuple<String,Integer>> inputMap, boolean floating, boolean screenFollow, int id) {
		super(name, true, position.x, position.y, colid, sprite);
		myMovement = new GameObjectAction(inputMap);
		myFuckingName=name;
		isFloating = floating;
		isScreenFollow = screenFollow;
		charMap=inputMap;
		originalPosition = new JGPoint(position.x, position.y);
		myID = id;
	}

	public GameObject(String name, JGPoint position, int colid, String sprite, Map<Integer, Tuple<String,Integer>> inputMap, boolean floating, int id) {
		super(name, true, position.x, position.y, colid, sprite);
		myMovement = new GameObjectAction(inputMap);
		myFuckingName=name;
		isFloating = floating;
		isScreenFollow = false;
		charMap=inputMap;
		originalPosition = new JGPoint(position.x, position.y);
		myID = id;
	}

	public GameObject(String name, JGPoint position, int colid, String sprite, boolean floating, int id) {
		super(name, true, position.x, position.y, colid, sprite);
		myMovement = new GameObjectAction();
		myFuckingName=name;
		isFloating = floating;
		isScreenFollow = false;
		originalPosition = new JGPoint(position.x, position.y);
		myID = id;
	}

	public void move() {
		if(myMovement.getIsStart()){
			myMovement.start(this);
		}
		//System.out.println("move");
	}

	public String getFuckingName() {
		return myFuckingName;
	}

	public boolean getIsFloating(){
		return isFloating;
	}

	public Map<Integer, Tuple<String,Integer>> getCharMap(){
		return charMap;
	}

	public GameObjectAction getMovement(){
		return myMovement;
	}

	public boolean getIsScreenFollowing(){
		return isScreenFollow;
	}
	public void setIsScreenFollowing(boolean b){
		isScreenFollow=b;
	}

	public int getID(){
		return myID;
	}

}
