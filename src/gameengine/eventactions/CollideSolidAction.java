package gameengine.eventactions;

import java.util.List;

import collisions.CollisionManager;
import util.positionalsystem.ObjectPlacementManager;
import util.positionalsystem.PositionalType;
import util.positionalsystem.RelativeHorizontalPosition;
import util.positionalsystem.RelativeVerticalPosition;
import util.tuples.Tuple;
import gameengine.GameObject;
import gameengine.gameevents.InvalidEventException;
import gameengine.gameevents.ParameterDesc;
import gameplayer.GameEventManager;
//import gameEngine.gameevents.GameEventManager;
import gameplayer.GamePlayerFrame;
import gameplayer.GamePlayerEngine;

public class CollideSolidAction extends GameEventAction {
	@ParameterDesc(name = "Recipient 1", description = "First object", type = Integer.class)
	protected int recipient1;
	@ParameterDesc(name = "Recipient 2", description = "Second object", type = Integer.class)
	protected int recipient2;
	protected Tuple<Integer> collisionRelation;

	public CollideSolidAction() {
		super();
	}
	public CollideSolidAction(List<String> arguments)
			throws InvalidEventActionException {
		super(arguments);
		if (arguments.size() < 2) {
			throw new InvalidEventActionException("Invalid parameters.");
		}
		collisionRelation = new Tuple<Integer>(Integer.parseInt(arguments
				.get(0)), Integer.parseInt(arguments.get(1)));
	}

	@Override
	public void act(GameEventManager manager, GamePlayerEngine gamePlayer) {
		boolean reversed = false;
		Tuple<Integer> collisionToCheck = collisionRelation.clone();
		if ((int) collisionToCheck.x > (int) collisionToCheck.y) {
			collisionToCheck.reverse();
			reversed = true;
		}
		for (Tuple<GameObject> i : gamePlayer.getCurrentCollisions().get(
				collisionToCheck)) {
			Tuple<GameObject> collisionIterator = i.clone();
			if (reversed) {
				collisionIterator.reverse();
			}
			RelativeHorizontalPosition lrhp = CollisionManager
					.getLastRelativeHorizontalPosition(collisionIterator.x,
							collisionIterator.y);
			RelativeVerticalPosition lrvp = CollisionManager
					.getLastRelativeVerticalPosition(collisionIterator.x,
							collisionIterator.y);
			ObjectPlacementManager.placeBeside(collisionIterator.x, collisionIterator.y,
					lrhp, lrvp);
		}
	}
}