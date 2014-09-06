package gameengine.powerups;

import jgame.JGTimer;
import gameengine.GameObject;

public class InvincibilityPowerUp extends PowerUp {

	public InvincibilityPowerUp(int usesLeft, String imageName) {
		super(usesLeft, imageName);
	}

	@Override
	public void useItem(final GameObject obj) {
		final String currString = obj.getImageName();
		obj.changeSprite(this.imageName);
		obj.colid = 0; // acts like an enemy?
		final int currColid = obj.getColID();
		final int timer = this.usesLeft * 30;
		new JGTimer(timer, true) {
			public void alarm() {
				obj.colid = currColid;
				obj.changeSprite(currString);
			}
		};
		obj.removePowerUp(this);
	}
}
