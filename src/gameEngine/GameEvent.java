package gameEngine;

import gameplayer.GameEventManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GameEvent {
	protected int sourceObjectId;
	protected List<Integer> recipientObjectIds;
	protected List<GameEventAction> actions;
	protected List<String> paramaters;
	protected boolean activated;
	protected boolean isOneTimeEvent;

	public GameEvent(int sourceObjectId, List<Integer> recipientObjectIds,
			List<String> parameters) throws InvalidEventException {
		this(sourceObjectId, recipientObjectIds, parameters, false);
	}

	public GameEvent(int sourceObjectId, List<Integer> recipientObjectIds,
			List<String> parameters, boolean isOneTimeEvent)
			throws InvalidEventException {
		this.sourceObjectId = sourceObjectId;
		this.recipientObjectIds = new ArrayList<Integer>();
		this.recipientObjectIds.addAll(recipientObjectIds);
		this.actions = new ArrayList<GameEventAction>();
		this.activated = false;
		this.isOneTimeEvent = isOneTimeEvent;
	}

	public boolean check(GameEventManager manager) {
		if (shouldTriggerEvent() && eventHappened()) {
			trigger(manager);
			activated = true;
			return true;
		}
		return false;
	}

	public void addAction(GameEventAction... actions) {
		this.actions.addAll(Arrays.asList(actions));
	}

	public boolean isOneTimeEvent() {
		return isOneTimeEvent;
	}

	protected void trigger(GameEventManager manager) {
		for (GameEventAction action : actions) {
			action.act(manager);
		}
	}

	protected abstract boolean eventHappened();

	private boolean shouldTriggerEvent() {
		return (isOneTimeEvent && !activated) || !isOneTimeEvent;
	}
}
