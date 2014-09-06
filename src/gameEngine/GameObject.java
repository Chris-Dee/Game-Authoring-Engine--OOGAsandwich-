package gameengine;

import gameplayer.MovementParameters;
import java.util.ArrayList;
import jgame.*;
import gameengine.powerups.*;

public class GameObject extends JGObject {

	private GameObjectMovement myMovement;
	private String prefixName;
	private boolean isFloating;
	private boolean isScreenFollow;
	public JGPoint originalPosition;
	private int myID;
	private UninstantiatedGameObject myUninstantiatedGameObject;
	private int myHitPoints;
	private int myColID;
	private int jumpSpeed = 10;
	private int moveSpeed = 5;
	public ArrayList<PowerUp> myItems;
	private boolean canJump;
	private int jumpCounter;

	public GameObject(String name, JGPoint position,
			int colid, String sprite,
			MovementParameters myMovementParams,
			boolean floating, boolean screenFollow, int id, int hitPoints,
			UninstantiatedGameObject obj, boolean updateID) {
		super(name, updateID, position.x, position.y, colid, sprite);
		myMovement = new GameObjectMovement(myMovementParams);
		myMovement.setInitialPosition(new JGPoint((int) this.x, (int) this.y));
//		myMovement.setEngine((gameplayer.GamePlayerEngine)this.eng);
		prefixName = name;
		isFloating = floating;
		isScreenFollow = screenFollow;
		originalPosition = new JGPoint(position.x, position.y);
		myID = id;
		myColID = colid;
		myUninstantiatedGameObject = obj;
		myHitPoints = hitPoints;
		myItems = new ArrayList<PowerUp>();
//		if (myMovementParams.getBehaviorName().equals("usercontrolled")) {
//			moveSpeed = myMovementParams.getDuration() / 10;
//			jumpSpeed = myMovementParams.getSpeed() * 2;
//		}
		canJump = true;
		jumpCounter = 0;
	}

	public UninstantiatedGameObject toUninstantiated() {
		return myUninstantiatedGameObject;
	}

	public void reset() {
		x = originalPosition.x;
		y = originalPosition.y;
	}

	public void changeSprite(String imageName) {
		setImage(imageName);
	}

	public void move() {
		if(this.name.equals("player"))
			System.out.println(canJump);
		if (myMovement.getIsStart()) {
			myMovement.start(this);
		}
		if(yspeed != 0){
			canJump = false;
		}
		else{
			
			jumpCounter++;
			if(jumpCounter == 5){
				canJump = true;
				jumpCounter = 0;
			}
		}

		// JGame was changed to create duplicates of objects with the name+180
		// as the object name. This code basically checks if the object iamges
		// need to be reset,
		// given their current velocities
		if (xspeed > 0
				&& getImageName().substring(getImageName().length() - 3)
						.equals("180"))
			setImage(getImageName().substring(0, getImageName().length() - 3));
		if (xspeed < 0
				&& !getImageName().substring(getImageName().length() - 3)
						.equals("180"))
			setImage(getImageName() + "180");
	}

	/**
	 * JGObject sometimes adds integers to the name. The prefixName is the exact
	 * String of the name that the user input into the GAE.
	 * 
	 * @return
	 */
	public String getPrefixName() {
		return prefixName;
	}

	public void setMovementPattern(String pattern) {
		myUninstantiatedGameObject.objectBehavior = pattern;
		GameObject g = editor_engine.addObject(
				myUninstantiatedGameObject.objectSprite,
				myUninstantiatedGameObject.objectPosition.x,
				myUninstantiatedGameObject.objectPosition.y, false);
		editor_engine.addSelectedObject(g);

		editor_engine.deleteObject(this);
	}

	public int getColID() {
		return myColID;
	}

	public boolean getIsFloating() {
		return isFloating;
	}

	public void setCollID(int collID) {
		myColID = collID;
		myUninstantiatedGameObject.objectColid = collID;
	}

	public void setIsFloating(boolean b) {
		isFloating = b;
		myUninstantiatedGameObject.objectFloating = b;
	}

	public String[] getAllStats() {
		return myUninstantiatedGameObject.getStats();
	}

	public GameObjectMovement getMovement() {
		return myMovement;
	}

	public boolean getIsScreenFollowing() {
		return isScreenFollow;
	}

	public void setIsScreenFollowing(boolean b) {
		isScreenFollow = b;
		myUninstantiatedGameObject.objectScreenFollow = b;
	}

	public void setSpeed(int speed) {
		myMovement.setSpeed(speed, this);
		myUninstantiatedGameObject.setSpeed(speed);
	}

	public void setDuration(int duration) throws InterruptedException {
		myMovement.setDuration(duration, this);
		myUninstantiatedGameObject.setDuration(duration * 10);
	}

	public int getID() {
		return myID;
	}

	public void changeHitPoints(int magnitude) {
		myHitPoints += magnitude;
	}

	public int getHitPoints() {
		return myHitPoints;
	}

	public void addPowerUp(PowerUp pu) {
		myItems.add(pu);
	}

	public void removePowerUp(PowerUp pu) {
		myItems.remove(pu);
	}

	public void changeName(String text) {
		prefixName = text;
		name = prefixName + (myID + 1);
		myUninstantiatedGameObject.setName(prefixName);
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(int newMoveSpeed) {
		moveSpeed = newMoveSpeed;
	}

	public int getJumpSpeed() {
		return jumpSpeed;
	}

	public void setJumpSpeed(int newJumpSpeed) {
		jumpSpeed = newJumpSpeed;
	}
	
	public void setFloat(boolean isfloat){
		this.isFloating = isfloat;
	}
	
	public boolean getCanJump(){
		return canJump;
	}
	
	public void setCanJump(boolean canJump){
		this.canJump = canJump;
	}
	
	public void incrementJumpCounter(){
		jumpCounter++;
	}
	
	public void resetJumpCounter(){
		jumpCounter = 0;
	}
	
	public int getJumpCounter(){
		return jumpCounter;
	}

}