package gameEngine;

import gameAuthoringEnvironment.levelEditor.ObjectStats;
import gameplayer.Tuple;

import java.util.Map;

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
	Map<Integer, Tuple<String, Integer>> objectInputMap;
	Integer objectID;

	public UninstantiatedGameObject(String name, JGPoint position,
			Integer colid, String sprite, String behavior, Integer time,
			Integer speed, Boolean floating, Integer id) {
		objectName = name;
		objectPosition = position;
		objectColid = colid;
		objectSprite = sprite;
		objectBehavior = behavior;
		objectTime = time;
		objectSpeed = speed;
		objectFloating = floating;
		objectScreenFollow = false;
		objectID = id;
	}

	public UninstantiatedGameObject(String name, JGPoint position,
			Integer colid, String sprite,
			Map<Integer, Tuple<String, Integer>> inputMap, boolean floating,
			Integer id) {
		objectName = name;
		objectPosition = position;
		objectColid = colid;
		objectSprite = sprite;
		objectInputMap = inputMap;
		objectFloating = floating;
		objectScreenFollow = false;
		objectID = id;
	}

	public UninstantiatedGameObject(String name, JGPoint position,
			Integer colid, String sprite,
			Map<Integer, Tuple<String, Integer>> inputMap, boolean floating,
			boolean screenFollow, Integer id) {
		objectName = name;
		objectPosition = position;
		objectColid = colid;
		objectSprite = sprite;
		objectInputMap = inputMap;
		objectFloating = floating;
		objectScreenFollow = screenFollow;
		objectID = id;
	}

	public UninstantiatedGameObject(String name, JGPoint position,
			Integer colid, String sprite, boolean floating, Integer id) {
		objectName = name;
		objectPosition = position;
		objectColid = colid;
		objectSprite = sprite;
		objectFloating = floating;
		objectScreenFollow = false;
		objectID = id;
	}

	public String[] getStats() {
		// TODO need to change the gravity thing to per object, and collID
		// String
		String[] toReturn = { objectName, objectColid.toString(),
				objectBehavior, objectSpeed.toString(),
				Integer.toString(((int) objectTime / 10)), "5",
				objectScreenFollow.toString(), objectSprite,
				objectFloating.toString() };
		return toReturn;
	}

	public void setMovementName(String move) {
		objectBehavior = move;
	}

	public void setSpeed(int speed) {
		objectSpeed = speed;
	}

	public void setDuration(int time) {
		objectTime = time;
	}

	public GameObject instantiate() {
		// System.out.println(objectTime);
		// TODO: Make this better.
		if (objectInputMap != null) {
			return new GameObject(objectName, objectPosition, objectColid,
					objectSprite, objectInputMap, objectFloating,
					objectScreenFollow, objectID, this);
		} else if (objectTime != null) {
			return new GameObject(objectName, objectPosition, objectColid,
					objectSprite, objectBehavior, objectTime, objectSpeed,
					objectFloating, objectID, this);
		} else {
			return new GameObject(objectName, objectPosition, objectColid,
					objectSprite, objectFloating, objectID, this);
		}
	}
}
