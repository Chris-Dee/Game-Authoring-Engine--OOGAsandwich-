package gameengine.eventactions;

import java.util.List;

import statistics.GameStats;
import gameengine.gameevents.ParameterDesc;
import gameplayer.GameEventManager;
import gameplayer.GamePlayerEngine;

public class IncreaseLivesAction extends GameEventAction {
	@ParameterDesc(name = "Number of lives to increment by", description = "Value", type = Integer.class)
	protected int lives;

	public IncreaseLivesAction() {
		super();
	}

	public IncreaseLivesAction(List<String> arguments)
			throws InvalidEventActionException {
		super(arguments);
		if (arguments.isEmpty()) {
			throw new InvalidEventActionException("Need number of lives to increment by");
		}
		lives = Integer.parseInt(arguments.get(0));
	}

	@Override
	public void act(GameEventManager manager, GamePlayerEngine gamePlayer) {
		gamePlayer.getGame().setLives(gamePlayer.getGame().getLives() + lives);
	}

}
