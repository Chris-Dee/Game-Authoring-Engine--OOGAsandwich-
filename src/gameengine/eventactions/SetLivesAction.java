package gameengine.eventactions;

import gameengine.gameevents.ParameterDesc;
import gameplayer.GameEventManager;
import gameplayer.GamePlayerEngine;

import java.util.List;

public class SetLivesAction extends GameEventAction {
	@ParameterDesc(name = "Number to set lives to", description = "Value", type = Integer.class)
	protected int lives;

	public SetLivesAction() {
		super();
	}

	public SetLivesAction(List<String> arguments)
			throws InvalidEventActionException {
		super(arguments);
		if (arguments.isEmpty()) {
			throw new InvalidEventActionException("Need a number to set lives");
		}
		lives = Integer.parseInt(arguments.get(0));
	}

	@Override
	public void act(GameEventManager manager, GamePlayerEngine gamePlayer) {
		gamePlayer.getGame().setLives(lives);
	}

}
