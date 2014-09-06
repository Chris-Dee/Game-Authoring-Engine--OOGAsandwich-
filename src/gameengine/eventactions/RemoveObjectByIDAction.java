package gameengine.eventactions;

import java.util.List;

import gameengine.gameevents.ParameterDesc;
import gameplayer.GameEventManager;
import gameplayer.GamePlayerEngine;

public class RemoveObjectByIDAction extends RemoveObjectAction {
	@ParameterDesc(name = "Object to remove", description = "The Object's unique id.", type = Integer.class)
	protected int recipientId;

	public RemoveObjectByIDAction() {
		super();
	}

	public RemoveObjectByIDAction(List<String> parameters)
			throws InvalidEventActionException {
		super(parameters);
	}

	@Override
	public void act(GameEventManager manager, GamePlayerEngine gamePlayer) {
		manager.getObjectByUniqueId(recipientId).remove();
	}

}
