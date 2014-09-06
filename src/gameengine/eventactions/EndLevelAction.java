package gameengine.eventactions;

import java.util.List;

import gameplayer.GameEventManager;
import gameplayer.GamePlayerEngine;

public class EndLevelAction extends GameEventAction {

	public EndLevelAction() {
		super();
	}

	public EndLevelAction(List<String> arguments)
			throws InvalidEventActionException {
		super(arguments);
	}

	@Override
	public void act(GameEventManager manager, GamePlayerEngine gamePlayer) {
		gamePlayer.endLevel();
	}

}
