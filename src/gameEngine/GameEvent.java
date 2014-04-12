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

	public GameEvent(int sourceObjectId, List<Integer> recipientObjectIds,
			List<String> parameters) throws InvalidEventException {
		this.sourceObjectId = sourceObjectId;
		this.recipientObjectIds = new ArrayList<Integer>();
		this.recipientObjectIds.addAll(recipientObjectIds);
		this.actions = new ArrayList<GameEventAction>();
		this.activated = false;
	}

	public boolean check(GameEventManager manager) {
		if (!activated && eventHappened()) {
			trigger(manager);
			activated = true;
			return true;
		}
		return false;
	}

	public void addAction(GameEventAction... actions) {
		this.actions.addAll(Arrays.asList(actions));
	}

	protected void trigger(GameEventManager manager) {
		for (GameEventAction action : actions) {
			action.act(manager);
		}
	}

	protected abstract boolean eventHappened();
}
