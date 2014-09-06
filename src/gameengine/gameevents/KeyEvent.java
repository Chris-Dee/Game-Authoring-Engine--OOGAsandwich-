package gameengine.gameevents;

import gameplayer.GamePlayerEngine;

import java.util.List;

public class KeyEvent extends GameEvent {
	@ParameterDesc(name = "Key", description = "Integer", type = Integer.class)
	protected int key;

	public KeyEvent() throws InvalidEventException {
		super();
	}

	public KeyEvent(List<String> parameters, String name)
			throws InvalidEventException {
		super(parameters, name);
		if (parameters.isEmpty()) {
			throw new InvalidEventException();
		}
		this.key = Integer.parseInt(parameters.get(0));
	}

	@Override
	protected boolean eventHappened(GamePlayerEngine gamePlayer) {
		return gamePlayer.getKey(key);
	}
}