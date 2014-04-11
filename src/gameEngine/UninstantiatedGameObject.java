package gameEngine;

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
	Map<Integer, Tuple<String,Integer>> objectInputMap;
	
	public UninstantiatedGameObject(String name, JGPoint position, Integer colid, String sprite, String behavior, Integer time, Integer speed, Boolean floating) {
		objectName = name;
		objectPosition = position;
		objectColid = colid;
		objectSprite = sprite;
		objectBehavior = behavior;
		objectTime = time;
		objectSpeed = speed;
		objectFloating = floating;
		objectScreenFollow = false;
	}

	public UninstantiatedGameObject(String name, JGPoint position, Integer colid, String sprite, Map<Integer, Tuple<String,Integer>> inputMap, boolean floating) {
		objectName = name;
		objectPosition = position;
		objectColid = colid;
		objectSprite = sprite;
		objectInputMap = inputMap;
		objectFloating = floating;
		objectScreenFollow = false;
	}
	
	public UninstantiatedGameObject(String name, JGPoint position, Integer colid, String sprite, Map<Integer, Tuple<String,Integer>> inputMap, boolean floating, boolean screenFollow) {
		objectName = name;
		objectPosition = position;
		objectColid = colid;
		objectSprite = sprite;
		objectInputMap = inputMap;
		objectFloating = floating;
		objectScreenFollow = screenFollow;
	}

	public UninstantiatedGameObject(String name, JGPoint position, Integer colid, String sprite, boolean floating) {
		objectName = name;
		objectPosition = position;
		objectColid = colid;
		objectSprite = sprite;
		objectFloating = floating;
		objectScreenFollow = false;
	}

	public GameObject instantiate(){
		// TODO: Make this better.
		if (objectInputMap != null) {
			return new GameObject(objectName, objectPosition, objectColid, objectSprite, objectInputMap, objectFloating, objectScreenFollow);
		} else if (objectTime != null) {
			return new GameObject(objectName, objectPosition, objectColid, objectSprite, objectBehavior, objectTime, objectSpeed, objectFloating);
		} else {
			return new GameObject(objectName, objectPosition, objectColid, objectSprite, objectFloating);
		}
	}
}
