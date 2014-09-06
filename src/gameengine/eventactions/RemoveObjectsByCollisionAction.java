package gameengine.eventactions;

import java.util.List;

import statistics.GameStats;
import util.tuples.Tuple;
import gameengine.GameObject;
import gameengine.gameevents.ParameterDesc;
import gameplayer.GameEventManager;
import gameplayer.GamePlayerEngine;

public class RemoveObjectsByCollisionAction extends GameEventAction {

	@ParameterDesc(name = "Object to Remove", description = "The Object's Collision ID", type = Integer.class)
	protected int objectToRemoveColid;
	@ParameterDesc(name = "Object it collides with", description = "The Object's Collision ID", type = Integer.class)
	protected int objectCollidedColid;

	public RemoveObjectsByCollisionAction() {
		super();
	}

	public RemoveObjectsByCollisionAction(List<String> arguments)
			throws InvalidEventActionException {
		super(arguments);
		if (arguments.size() < 2) {
			throw new InvalidEventActionException("Invalid Parameters");
		}
		objectToRemoveColid = Integer.parseInt(arguments.get(0));
		objectCollidedColid = Integer.parseInt(arguments.get(1));
	}

	@Override
	public void act(GameEventManager manager, GamePlayerEngine gamePlayer) {
		Tuple<Integer> collisionRelation = new Tuple<Integer>(
				objectToRemoveColid, objectCollidedColid);
		collisionRelation.sort();
		if (gamePlayer.getCurrentCollisions().containsKey(collisionRelation)) {
			for (Tuple<GameObject> objects : gamePlayer.getCurrentCollisions()
					.get(collisionRelation)) {
				if (objects.x.colid == objectToRemoveColid) {
					gamePlayer.getCurrentObjects().remove(objects.x);
					objects.x.remove();
				} else {
					gamePlayer.getCurrentObjects().remove(objects.y);
					objects.y.remove();
				}
			}
		}
		if(objectToRemoveColid==4){
			GameStats.update("Enemies Killed", 1);
		}
	}
}
