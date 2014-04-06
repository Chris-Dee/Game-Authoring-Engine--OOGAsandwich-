package gameEngine;

import jgame.JGPoint;

public class UninstantiatedGameObject {
	String objectName;
	JGPoint objectPosition;
	int objectColid;
	String objectSprite;
	GameObjectAction objectMovement;
	
	public UninstantiatedGameObject(String name, JGPoint position, int colid, String sprite, GameObjectAction move) {
		objectName = name;
		objectPosition = position;
		objectColid = colid;
		objectSprite = sprite;
		objectMovement = move;
	}
	public GameObject instantiate(){
		return new GameObject(objectName, objectPosition, objectColid, objectSprite, objectMovement);
	}
}
