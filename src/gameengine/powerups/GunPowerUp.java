package gameengine.powerups;

import jgame.JGPoint;
import gameengine.GameObject;
import gameengine.UninstantiatedGameObject;
import gameplayer.GamePlayerEngine;
import gameplayer.MovementParameters;

public class GunPowerUp extends PowerUp {
	private static final int BULLET_SPEED=10;
	private int bulletDirection;

	public GunPowerUp(int usesLeft, String imageName) {
		super(usesLeft, imageName);
		bulletDirection=0;
	}

	@Override
	public void useItem(GameObject obj) {
		GameObject currentBullet = new GameObject("bullet", new JGPoint(
				(int) obj.x, (int) obj.y), 150, "bullet", new MovementParameters("none", 0,0), true,
				false, 1500, 0, new UninstantiatedGameObject("bullet",
						new JGPoint((int) obj.x, (int) obj.y), 150, "bullet",
						new MovementParameters("none", 0,0), true, false, 15, 0), true);
		GamePlayerEngine.addObjects(currentBullet);
		currentBullet.xspeed = Math.cos(Math.toDegrees(Math
				.toDegrees(bulletDirection))) * BULLET_SPEED;
		currentBullet.yspeed = Math.sin(Math.toDegrees(Math
				.toDegrees(bulletDirection))) * BULLET_SPEED;
		this.usesLeft--;
		if (this.usesLeft <= 0) {
			obj.removePowerUp(this);
		}
	}

}
