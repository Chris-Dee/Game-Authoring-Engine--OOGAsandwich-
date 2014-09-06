package gameengine;

import gameengine.powerups.PowerUp;
import gameplayer.MovementParameters;

import java.util.Collection;

import jgame.JGPoint;

public class UninstantiatedGameObject {
	String objectName;
	JGPoint objectPosition;
	Integer objectColid;
	String objectSprite;
	String objectBehavior;
	Integer objectTime;
	Integer objectSpeed;
	Boolean objectFloating;
	Boolean objectScreenFollow;
	Integer objectID;
	Integer objectHitPoints;
	MovementParameters myMovementParams;

	public UninstantiatedGameObject(String name, JGPoint position,
			Integer colid, String sprite, MovementParameters myMovement,
			Boolean floating, Boolean screenFollow, Integer id,
			Integer hitPoints) {
		objectName = name;
		objectPosition = position;
		objectColid = colid;
		objectSprite = sprite;
		objectBehavior = myMovement.getBehaviorName();
		objectTime = myMovement.getDuration();
		objectSpeed = myMovement.getSpeed();
		myMovementParams = myMovement;
		objectFloating = floating;
		objectID = id;
		objectHitPoints = hitPoints;
		objectScreenFollow = screenFollow;
	}

	public String[] getStats() {
		return new String[] { objectName, objectColid.toString(),
				objectBehavior, Integer.toString(objectSpeed),
				Integer.toString(((int) objectTime / 10)),
				objectScreenFollow.toString(), objectSprite,
				objectFloating.toString(), Integer.toString(objectHitPoints) };
	}

	public void setMovementName(String move) {
		objectBehavior = move;
	}

	public void setSpeed(int speed) {
		objectSpeed = speed;
	}

	public void setName(String name) {
		objectName = name;
	}

	public void setDuration(int time) {
		objectTime = time;
	}

	public String getName() {
		return objectName;
	}

	public int getColID() {
		return objectColid;
	}

	public GameObject instantiate(boolean b) {
		return new GameObject(objectName, objectPosition, objectColid,
				objectSprite, myMovementParams, objectFloating,
				objectScreenFollow, objectID, objectHitPoints, this, b);
	}
}