package gameengine.powerups;

import jgame.JGTimer;
import gameengine.GameObject;

public class SpeedPowerUp extends PowerUp {
	private static final int SPEED_MULTIPLIER = 2;
	private static final int ONE_SECOND = 30;
	public SpeedPowerUp(int usesLeft, String imageName) {
		super(usesLeft, imageName);
	}

	@Override
	public void useItem(final GameObject obj) {
		final int currSpeed = obj.getMoveSpeed();
		obj.setMoveSpeed(currSpeed*SPEED_MULTIPLIER);
		final int currJump = obj.getJumpSpeed();
		obj.setJumpSpeed(currJump*SPEED_MULTIPLIER);
		final int timer = this.usesLeft * ONE_SECOND;
		new JGTimer(timer, true) {
			public void alarm() {
				obj.setMoveSpeed(currSpeed);
				obj.setJumpSpeed(currJump);
			}
		};
		obj.removePowerUp(this);
	}
}
