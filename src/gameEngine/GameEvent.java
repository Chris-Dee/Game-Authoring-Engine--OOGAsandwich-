package gameEngine;

import java.util.Arrays;
import java.util.List;

public abstract class GameEvent {
	int sourceObjectId;
	List<Integer> recipientObjectIds;
	List<GameEventAction> actions;

	public GameEvent(int sourceObjectId, Integer... recipientObjectIds) {
		this.sourceObjectId = sourceObjectId;
		this.recipientObjectIds.addAll(Arrays.asList(recipientObjectIds));
	}

	public void check() {
		if (eventHappened()) {
			trigger();
		}
	}

	public void addAction(GameEventAction... actions) {
		this.actions.addAll(Arrays.asList(actions));
	}

	protected void trigger() {
		for (GameEventAction action : actions) {
			action.act();
		}
	}

	protected abstract boolean eventHappened();
}
