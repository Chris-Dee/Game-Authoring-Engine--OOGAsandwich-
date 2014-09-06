package gameengine.gameevents;

import gameplayer.GamePlayerEngine;

import java.util.List;

import util.tuples.Tuple;

public class CollisionEvent extends GameEvent {
	@ParameterDesc(name = "Collision ID 1", description = "Object Type / Collision ID", type = Integer.class)
	protected int colid1;
	@ParameterDesc(name = "Collision ID 2", description = "Object Type / Collision ID", type = Integer.class)
	protected int colid2;

	private Tuple<Integer> collisionRelation;

	public CollisionEvent() throws InvalidEventException {
		super();
	}

	public CollisionEvent(List<String> parameters, String name)
			throws InvalidEventException {
		super(parameters, name);
		if (parameters.isEmpty()) {
			throw new InvalidEventException();
		}
		this.colid1 = Integer.parseInt(parameters.get(0));
		this.colid2 = Integer.parseInt(parameters.get(1));
		collisionRelation = new Tuple<Integer>(Integer.parseInt(parameters
				.get(0)), Integer.parseInt(parameters.get(1)));
	}

	@Override
	protected boolean eventHappened(GamePlayerEngine gamePlayer) {
		if (gamePlayer.getCurrentCollisions().containsKey(collisionRelation)) {
			// System.out.println("Collision relation exists: " +
			// collisionRelation);
			return true;
		}
		return false;
	}
}