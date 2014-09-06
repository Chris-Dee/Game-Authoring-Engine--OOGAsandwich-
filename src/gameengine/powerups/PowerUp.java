package gameengine.powerups;

import gameengine.GameObject;

public abstract class PowerUp {
	protected int usesLeft;
	protected String imageName;
	public PowerUp(int usesLeft,String imageName) {
		this.usesLeft=usesLeft;
		this.imageName=imageName;
	}
	
	public abstract void useItem(GameObject obj);

}
