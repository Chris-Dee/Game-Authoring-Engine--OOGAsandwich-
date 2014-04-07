package gameEngine;

import java.util.Map;

import jgame.JGPoint;

public class UninstantiatedGameObject {
	Constructor constructor;
	String objectName;
	JGPoint objectPosition;
	int objectColid;
	String objectSprite;
	String objectBehavior;
	int objectTime;
	int objectSpeed;
	boolean objectFloating;
	Map<Integer, String> objectInputMap;
	
	public UninstantiatedGameObject(String name, JGPoint position, int colid, String sprite, String behavior, int time, int speed, boolean floating) {
		objectName = name;
		objectPosition = position;
		objectColid = colid;
		objectSprite = sprite;
		objectBehavior = behavior;
		objectTime = time;
		objectSpeed = speed;
		objectFloating = floating;
		constructor = new Constructor() {
			@Override
			public GameObject construct() {
				return new GameObject(objectName, objectPosition, objectColid, objectSprite,
					objectBehavior, objectTime, objectSpeed, objectFloating);
			}
		};
	}

	public UninstantiatedGameObject(String name, JGPoint position, int colid, String sprite, Map<Integer, String> inputMap, boolean floating) {
		objectName = name;
		objectPosition = position;
		objectColid = colid;
		objectSprite = sprite;
		objectInputMap = inputMap;
		objectFloating = floating;
		constructor = new Constructor() {
			@Override
			public GameObject construct() {
				return new GameObject(objectName, objectPosition, objectColid, objectSprite,
					objectInputMap, objectFloating);
			}
		};
	}

	public UninstantiatedGameObject(String name, JGPoint position, int colid, String sprite, boolean floating) {
		objectName = name;
		objectPosition = position;
		objectColid = colid;
		objectSprite = sprite;
		objectFloating = floating;
		constructor = new Constructor() {
			@Override
			public GameObject construct() {
				return new GameObject(objectName, objectPosition, objectColid, objectSprite,
					objectFloating);
			}
		};
	}

	public GameObject instantiate(){
		return constructor.construct();
	}
}

class Constructor {
	public GameObject construct() {
		return null;
	}
}
