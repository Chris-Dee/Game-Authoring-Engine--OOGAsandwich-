package gameEngine;

import gameAuthoringEnvironment.levelEditor.ObjectStats;
import gameplayer.MethodData;

import java.util.Map;

import jgame.*;
import jgame.JGImage;

public class GameObject extends JGObject {

	private GameObjectAction myMovement;
	private String myFuckingName;
	private boolean isFloating;
	private boolean isScreenFollow;
	private Map<Integer, MethodData<String, Integer>> charMap;
	public JGPoint originalPosition;
	private int myID;
	private UninstantiatedGameObject myUninstantiatedGameObject;

	// private int myHitPoints;

	public GameObject(String name, JGPoint position, int colid, String sprite,
			String behavior, int time, int speed, boolean floating, int id,
			UninstantiatedGameObject obj) {
		super(name, true, position.x, position.y, colid, sprite);
		myMovement = new GameObjectAction(behavior, time, speed);
		myMovement.setInitialPosition(new JGPoint((int)this.x,(int)this.y));
		myFuckingName = name;
		isFloating = floating;
		isScreenFollow = false;
		originalPosition = new JGPoint(position.x, position.y);
		myID = id;
		myUninstantiatedGameObject = obj;
		// myHitPoints=hitPoints;
	}

	public void reset() {
		x = originalPosition.x;
		y = originalPosition.y;
		changeSprite("mobile");
		//setBBox(0,0, 50, 50);
	}
	
	public void changeSprite(String imageName) {
		setImage(imageName);
	}
	
	/*
	public void scaleSprite(int width, int height) {
		JGImage newImage;
		newImage.loadImage(getImageName());
		newImage.scale(width, height);
	}
	*/
	public GameObject(String name, JGPoint position, int colid, String sprite,
			Map<Integer, MethodData<String, Integer>> inputMap, boolean floating,
			boolean screenFollow, int id, UninstantiatedGameObject obj) {
		super(name, true, position.x, position.y, colid, sprite);
		myMovement = new GameObjectAction(inputMap);
		myFuckingName = name;
		isFloating = floating;
		isScreenFollow = screenFollow;
		charMap = inputMap;
		originalPosition = new JGPoint(position.x, position.y);
		myID = id;
		myUninstantiatedGameObject = obj;
	}

	public GameObject(String name, JGPoint position, int colid, String sprite,
			Map<Integer, MethodData<String, Integer>> inputMap, boolean floating,
			int id, UninstantiatedGameObject obj) {
		super(name, true, position.x, position.y, colid, sprite);
		myMovement = new GameObjectAction(inputMap);
		myFuckingName = name;
		isFloating = floating;
		isScreenFollow = false;
		charMap = inputMap;
		originalPosition = new JGPoint(position.x, position.y);
		myID = id;
		myUninstantiatedGameObject = obj;
	}

	public GameObject(String name, JGPoint position, int colid, String sprite,
			boolean floating, int id, UninstantiatedGameObject obj) {
		super(name, true, position.x, position.y, colid, sprite);
		myMovement = new GameObjectAction();
		myFuckingName = name;
		isFloating = floating;
		isScreenFollow = false;
		originalPosition = new JGPoint(position.x, position.y);
		myID = id;
		myUninstantiatedGameObject = obj;
	}
	
	public UninstantiatedGameObject toUninstantiated() {
		return myUninstantiatedGameObject;
	}

	public void move() {
		if (myMovement.getIsStart()) {
			myMovement.start(this);
		}
		// System.out.println("move");
	}

	public String getFuckingName() {
		return myFuckingName;
	}

	public boolean getIsFloating() {
		return isFloating;
	}
	public void setCollID(int collID){
		myID=collID;
		myUninstantiatedGameObject.objectColid=collID;
	}
	public void setIsFloating(boolean b){
		isFloating=b;
		myUninstantiatedGameObject.objectFloating=b;
	}
	public String[] getAllStats(){
		return myUninstantiatedGameObject.getStats();
	}

	public Map<Integer, MethodData<String, Integer>> getCharMap() {
		return charMap;
	}

	public GameObjectAction getMovement() {
		return myMovement;
	}

	public boolean getIsScreenFollowing() {
		return isScreenFollow;
	}

	public void setIsScreenFollowing(boolean b) {
		isScreenFollow = b;
		myUninstantiatedGameObject.objectScreenFollow=b;
	}
	public void setSpeed(int speed){
		myMovement.setSpeed(speed,this);
		myUninstantiatedGameObject.setSpeed(speed);
	}
	public void setDuration(int duration) throws InterruptedException{
	myMovement.setDuration(duration,this);
	myUninstantiatedGameObject.setDuration(duration*10);
	}
	public int getID() {
		return myID;
	}

}
