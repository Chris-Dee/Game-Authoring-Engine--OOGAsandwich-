package gameEngine.gameevents;

import java.util.List;

import gameEngine.GameEvent;
import gameEngine.InvalidEventException;

public class TimerEvent extends GameEvent {
	protected int delay;
	protected float startTime;

	public TimerEvent(int sourceObjectId, List<Integer> recipientObjectIds,
			List<String> parameters) throws InvalidEventException {
		super(sourceObjectId, recipientObjectIds, parameters);
		if (parameters.isEmpty()) {
			throw new InvalidEventException("Need a time.");
		}
		startTime = System.currentTimeMillis();
	}

	@Override
	protected boolean eventHappened() {
		return System.currentTimeMillis() > startTime + delay;
	}
}
