package gameengine.gameevents;

import java.util.HashMap;
import java.util.Arrays;
import java.util.List;

import gameengine.eventactions.InvalidEventActionException;
import gameplayer.GamePlayerEngine;

public class TimerEvent extends GameEvent {
	private static final int FRAMES_PER_DELAY_UNIT = 30;
	@ParameterDesc(name = "Delay", description = "Number of frames to wait before triggering the timer.", type = Integer.class)
	protected int delay;
	protected long startTime;

	public TimerEvent() throws InvalidEventException {
		super();
	}

	public TimerEvent(List<String> parameters, String name)
			throws InvalidEventException {
		super(parameters, name);
		if (parameters.isEmpty()) {
			throw new InvalidEventException();
		}
		startTime = 0;
		delay = FRAMES_PER_DELAY_UNIT * Integer.parseInt(parameters.get(0));
		this.delay = FRAMES_PER_DELAY_UNIT * Integer.parseInt(parameters.get(0));

	}

	@Override
	protected boolean eventHappened(GamePlayerEngine gamePlayer) {
		startTime += 1;
		return startTime >= delay;
	}
}

