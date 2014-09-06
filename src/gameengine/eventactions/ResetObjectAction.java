package gameengine.eventactions;

import gameengine.GameObject;
import gameengine.gameevents.ParameterDesc;
import gameplayer.GameEventManager;
import gameplayer.GamePlayerEngine;

import java.util.List;

public class ResetObjectAction extends GameEventAction {

	@ParameterDesc(name = "Object to reset", description = "The Object's collision id", type = Integer.class)
	protected int recipientId;

	public ResetObjectAction() {
		super();
	}

	public ResetObjectAction(List<String> arguments)
			throws InvalidEventActionException {
		super(arguments);
		if (arguments.isEmpty()) {
			throw new InvalidEventActionException("Need a recipient");
		}
		recipientId = Integer.parseInt(arguments.get(0));

	}

	@Override
	public void act(GameEventManager manager, GamePlayerEngine gamePlayer) {
		for (GameObject obj : manager.getObjectsByColId(recipientId)) {
			obj.reset();
		}

	}

}
