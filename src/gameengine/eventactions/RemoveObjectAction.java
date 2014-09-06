package gameengine.eventactions;

import java.util.List;

import gameengine.GameObject;
import gameengine.gameevents.ParameterDesc;
import gameplayer.GameEventManager;
import gameplayer.GamePlayerEngine;

public class RemoveObjectAction extends GameEventAction {

	@ParameterDesc(name = "Recipient ID", description = "Object to remove by collision id.", type = Integer.class)
	protected int recipientId;

	public RemoveObjectAction() {
		super();
	}

	public RemoveObjectAction(List<String> arguments)
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
			obj.remove();
		}
	}
}
