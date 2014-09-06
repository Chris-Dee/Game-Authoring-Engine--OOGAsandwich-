package gameengine.gameevents;

import java.util.List;

import gameplayer.GamePlayerEngine;

public class ScoreEvent extends GameEvent {
	@ParameterDesc(name = "Score", description = "Game's total target score", type = Integer.class)
	protected int score;

	public ScoreEvent() throws InvalidEventException {
		super();
	}

	public ScoreEvent(List<String> parameters, String name)
			throws InvalidEventException {
		super(parameters, name);
		if (parameters.isEmpty()) {
			throw new InvalidEventException();
		}
		this.score = Integer.parseInt(parameters.get(0));
	}

	@Override
	protected boolean eventHappened(GamePlayerEngine gamePlayer) {
		return (gamePlayer.getScore() >= score);
	}

}
