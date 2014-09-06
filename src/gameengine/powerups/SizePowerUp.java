package gameengine.powerups;

import jgame.JGTimer;
import gameengine.GameObject;

public class SizePowerUp extends PowerUp {

	public SizePowerUp(int usesLeft, String imageName) {
		super(usesLeft, imageName);
	}

	@Override
	public void useItem(final GameObject obj) {
		final String currString = obj.getImageName();
		obj.changeSprite(this.imageName);
		final int timer = this.usesLeft * 30;
		new JGTimer(timer, true) {
			public void alarm() {
				obj.changeSprite(currString);
			}
		};
		obj.removePowerUp(this);
	}
}
