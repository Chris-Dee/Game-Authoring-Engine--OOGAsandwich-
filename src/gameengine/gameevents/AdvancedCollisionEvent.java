package gameengine.gameevents;

import gameengine.GameObject;
import gameplayer.GamePlayerEngine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.positionalsystem.RelativePosition;
import util.tuples.Tuple;
import collisions.CollisionManager;

public class AdvancedCollisionEvent extends GameEvent {
	@ParameterDesc(name = "Colliding Object Collision ID", description = "Object Type / Collision ID", type = Integer.class)
	protected int colid1;
	@ParameterDesc(name = "Collidee Object Collision ID", description = "Object Type / Collision ID", type = Integer.class)
	protected int colid2;
	@ParameterDesc(name = "Sides To Collide", description = "top right bottom left continued", type = Boolean.class)
	protected boolean b;
	/*
	 * @ParameterDesc(name = "Vertical Collision Type", description =
	 * "Vertical Collsion Type", type = RelativeVerticalPosition.class)
	 * protected RelativeVerticalPosition rvp;
	 * 
	 * @ParameterDesc(name = "Horizontal Collision Type", description =
	 * "Horizontal Collsion Type", type = RelativeHorizontalPosition.class)
	 * protected RelativeHorizontalPosition rhp;
	 */
	private Tuple<Integer> collisionRelation;
	private Set<RelativePosition> checkedPositions;

	public AdvancedCollisionEvent() throws InvalidEventException {
		super();
	}

	public AdvancedCollisionEvent(List<String> parameters, String name)
			throws InvalidEventException {
		super(parameters, name);
		if (parameters.isEmpty()) {
			throw new InvalidEventException();
		}
		this.colid1 = Integer.parseInt(parameters.get(0));
		this.colid2 = Integer.parseInt(parameters.get(1));
		checkedPositions = new HashSet<RelativePosition>();
		System.out.println(Boolean.parseBoolean(parameters.get(2)));

		if (Boolean.parseBoolean(parameters.get(2))) {
			checkedPositions.add(RelativePosition.TOP);
		}
		if (Boolean.parseBoolean(parameters.get(3))) {
			checkedPositions.add(RelativePosition.RIGHT);
		}
		if (Boolean.parseBoolean(parameters.get(4))) {
			checkedPositions.add(RelativePosition.BOT);
		}
		if (Boolean.parseBoolean(parameters.get(5))) {
			checkedPositions.add(RelativePosition.LEFT);
		}
		if (Boolean.parseBoolean(parameters.get(6))) {
			checkedPositions.add(RelativePosition.MIDDLE);
		}

		collisionRelation = new Tuple<Integer>(Integer.parseInt(parameters
				.get(0)), Integer.parseInt(parameters.get(1)));
	}

	@Override
	protected boolean eventHappened(GamePlayerEngine gamePlayer) {
		Tuple<Integer> collisionToCheck = collisionRelation.clone();
		if ((int) collisionToCheck.x > (int) collisionToCheck.y) {
			collisionToCheck.reverse();
		}
		Map<Tuple<Integer>, ArrayList<Tuple<GameObject>>> collisions = gamePlayer
				.getCurrentCollisions();
		if (collisions.containsKey(collisionRelation)) {
			for (Tuple<GameObject> collision : collisions
					.get(collisionRelation)) {
				RelativePosition rp = CollisionManager.getLastRelativePosition(
						collision.x, collision.y);
				if (checkedPositions.contains(rp)) {
					return true;
				}
			}
		}
		return false;
	}
}