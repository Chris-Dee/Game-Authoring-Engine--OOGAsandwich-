package gameEngine.gameevents;

import java.util.List;

import gameEngine.GameEvent;
import gameEngine.InvalidEventException;

public class TimerEvent extends GameEvent {
	protected int delay;
	protected long startTime;

	public TimerEvent(int sourceObjectId, List<Integer> recipientObjectIds,
			List<String> parameters) throws InvalidEventException {
		super(sourceObjectId, recipientObjectIds, parameters);
		if (parameters.isEmpty()) {
			throw new InvalidEventException("Need a time.");
		}
		startTime = 0;
		delay = 30 * Integer.parseInt(parameters.get(0));
	}

	@Override
	protected boolean eventHappened() {
		startTime += 1;
		return startTime >= delay;
	}
}
