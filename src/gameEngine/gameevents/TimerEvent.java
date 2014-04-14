package gameEngine.gameevents;

import java.util.List;

import gameEngine.GameEvent;
import gameEngine.InvalidEventException;
import gameEngine.ParameterDesc;

public class TimerEvent extends GameEvent {
	@ParameterDesc(name = "Delay", description = "Number of frames to wait before triggering the timer.")
	protected int delay;
	protected long startTime;

	public TimerEvent(int sourceObjectId, List<Integer> recipientObjectIds,
			List<String> parameters) throws InvalidEventException {
		super(sourceObjectId, recipientObjectIds, parameters, true);
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
