package gameEngine;

import jgame.JGPoint;

public class UninstantiatedGameObject {
	String objectName;
	JGPoint objectPosition;
	int objectColid;
	String objectSprite;
	public UninstantiatedGameObject(String name, JGPoint position, int colid, String sprite) {
		objectName = name;
		objectPosition = position;
		objectColid = colid;
		objectSprite = sprite;
	}
	public GameObject instantiate(){
		return new GameObject(objectName, objectPosition, objectColid, objectSprite);
	}
}
