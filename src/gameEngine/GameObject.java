package gameEngine;

import jgame.*;
import jgame.JGObject;

public class GameObject extends JGObject {
	
	public GameObject(String name, JGPoint position, int colid, String sprite) {
		super(name, true, position.x, position.y, colid, sprite);
	}
	
	public void move() {
		
	}

}
