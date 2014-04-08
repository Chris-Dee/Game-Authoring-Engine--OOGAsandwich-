package gameEngine;

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
	Map<Integer, String> objectInputMap;
	
	public UninstantiatedGameObject(String name, JGPoint position, Integer colid, String sprite, String behavior, Integer time, Integer speed, Boolean floating) {
		objectName = name;
		objectPosition = position;
		objectColid = colid;
		objectSprite = sprite;
		objectBehavior = behavior;
		objectTime = time;
		objectSpeed = speed;
		objectFloating = floating;
	}

	public UninstantiatedGameObject(String name, JGPoint position, Integer colid, String sprite, Map<Integer, String> inputMap, boolean floating) {
		objectName = name;
		objectPosition = position;
		objectColid = colid;
		objectSprite = sprite;
		objectInputMap = inputMap;
		objectFloating = floating;
	}

	public UninstantiatedGameObject(String name, JGPoint position, Integer colid, String sprite, boolean floating) {
		objectName = name;
		objectPosition = position;
		objectColid = colid;
		objectSprite = sprite;
		objectFloating = floating;
	}

	public GameObject instantiate(){
		// TODO: Make this better.
		if (objectInputMap != null) {
			return new GameObject(objectName, objectPosition, objectColid, objectSprite, objectInputMap, objectFloating);
		} else if (objectTime != null) {
			return new GameObject(objectName, objectPosition, objectColid, objectSprite, objectBehavior, objectTime, objectSpeed, objectFloating);
		} else {
			return new GameObject(objectName, objectPosition, objectColid, objectSprite, objectFloating);
		}
	}
}
