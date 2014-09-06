package gameengine.eventactions;

import java.util.List;

import statistics.GameStats;
import gameengine.gameevents.ParameterDesc;
import gameplayer.GameEventManager;
import gameplayer.GamePlayerEngine;

public class ChangeScoreAction extends GameEventAction {
	@ParameterDesc(name = "Score to increment by", description = "Value", type = Integer.class)
	protected int scoreChange;

	public ChangeScoreAction() {
		super();
	}

	public ChangeScoreAction(List<String> arguments)
			throws InvalidEventActionException {
		super(arguments);
		if (arguments.isEmpty()) {
			throw new InvalidEventActionException("Need score value to increment by");
		}
		scoreChange = Integer.parseInt(arguments.get(0));
	}

	@Override
	public void act(GameEventManager manager, GamePlayerEngine gamePlayer) {
		gamePlayer.getGame().setTotalScore(
				gamePlayer.getGame().getTotalScore() + scoreChange);
		GameStats.update("Score", scoreChange);
	}

}
