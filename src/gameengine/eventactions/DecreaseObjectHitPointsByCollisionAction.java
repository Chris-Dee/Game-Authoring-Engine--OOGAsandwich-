package gameengine.eventactions;

import java.util.List;

import statistics.GameStats;
import util.tuples.Tuple;
import gameengine.GameObject;
import gameengine.gameevents.ParameterDesc;
import gameplayer.GameEventManager;
import gameplayer.GamePlayerEngine;

public class DecreaseObjectHitPointsByCollisionAction extends GameEventAction {

	@ParameterDesc(name = "Object to Decrease Hit Points", description = "The Object's Collision ID", type = Integer.class)
	protected int objectToDecreaseHitPointsColid;
	@ParameterDesc(name = "Object it collides with", description = "The Object's Collision ID", type = Integer.class)
	protected int objectCollidedColid;

	public DecreaseObjectHitPointsByCollisionAction() {
		super();
	}

	public DecreaseObjectHitPointsByCollisionAction(List<String> arguments)
			throws InvalidEventActionException {
		super(arguments);
		if (arguments.size() < 2) {
			throw new InvalidEventActionException("Invalid Parameters");
		}
		objectToDecreaseHitPointsColid = Integer.parseInt(arguments.get(0));
		objectCollidedColid = Integer.parseInt(arguments.get(1));
	}

	@Override
	public void act(GameEventManager manager, GamePlayerEngine gamePlayer) {
		Tuple<Integer> collisionRelation = new Tuple<Integer>(
				objectToDecreaseHitPointsColid, objectCollidedColid);
		collisionRelation.sort();
		if (gamePlayer.getCurrentCollisions().containsKey(collisionRelation)) {
			for (Tuple<GameObject> objects : gamePlayer.getCurrentCollisions()
					.get(collisionRelation)) {
				if (objects.x.colid == objectToDecreaseHitPointsColid) {
					objects.x.changeHitPoints(-1);
					if(objects.x.getHitPoints()<=0){
						gamePlayer.getCurrentObjects().remove(objects.x);
						objects.x.remove();
					}
					objects.y.yspeed=-objects.y.yspeed;
				} else {
					objects.y.changeHitPoints(-1);
					if(objects.y.getHitPoints()<=0){
						gamePlayer.getCurrentObjects().remove(objects.y);
						objects.y.remove();
					}
					objects.x.yspeed=-objects.x.yspeed;
				}
			}
		}
	}
}
