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
		this.activated = false;
	}

	public boolean check(GameEventManager manager) {
		System.out.println("not activated and happened: " + (!activated && eventHappened()));
		if (!activated && eventHappened()) {
			System.out.printf("GameEvent triggering (%d)!\n", actions.size());
			trigger(manager);
			activated = true;
			System.out.println("GameEvent#check() returning true");
			return true;
		}
		System.out.println("GameEvent#check() returning false");
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
