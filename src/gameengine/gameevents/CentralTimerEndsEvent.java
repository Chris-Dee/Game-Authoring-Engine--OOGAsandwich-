package gameengine.gameevents;

import java.util.List;

import gameplayer.GamePlayerEngine;

public class CentralTimerEndsEvent extends GameEvent {

	public CentralTimerEndsEvent() throws InvalidEventException {
		super();
	}

	public CentralTimerEndsEvent(List<String> parameters, String name)
			throws InvalidEventException {
		super(parameters, name);
	}

	@Override
	protected boolean eventHappened(GamePlayerEngine gamePlayer) {
		return gamePlayer.getGame().getCurrentLevel().getCurrentTime() < 0;
	}
}
