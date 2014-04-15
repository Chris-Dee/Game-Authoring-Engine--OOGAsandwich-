package gameEngine;

import gameplayer.GameEventManager;
import gameplayer.GamePlayerGame;

import java.lang.reflect.Field;
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

	/**
	 * Initializes a GameEvent that happens more than once.
	 * 
	 * @param sourceObjectId
	 * @param recipientObjectIds
	 * @param parameters
	 *            Optional parameters for events. They are Strings but can be
	 *            casted to whatever in classes that extend this.
	 * @throws InvalidEventException
	 *             Thrown when invalid parameters
	 *             (sourceObjectId/recipientObjectIds/parameters) are given.
	 */
	public GameEvent(int sourceObjectId, List<Integer> recipientObjectIds,
			List<String> parameters) throws InvalidEventException {
		this(sourceObjectId, recipientObjectIds, parameters, false);
	}

	/**
	 * Initializes a GameEvent
	 * 
	 * @param sourceObjectId
	 * @param recipientObjectIds
	 * @param parameters
	 *            Optional parameters for events. They are Strings but can be
	 *            casted to whatever in classes that extend this.
	 * @param isOneTimeEvent
	 *            Indicate whether the event should happen once or more than
	 *            once.
	 * @throws InvalidEventException
	 *             Thrown when invalid parameters
	 *             (sourceObjectId/recipientObjectIds/parameters) are given.
	 */
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

	/**
	 * Checks to see if conditions for event to happen have been satisfied, and
	 * if so, then run all of the GameEventActions.
	 * 
	 * @param manager
	 *            Used to retrieve actual objects.
	 * @return Whether the event was triggered. Can be used to remove the event
	 *         if it is a one time event, for efficiency.
	 */
	public boolean check(GameEventManager manager, GamePlayerGame gamePlayer) {
		if (shouldTriggerEvent() && eventHappened()) {
			trigger(manager, gamePlayer);
			activated = true;
			return true;
		}
		return false;
	}

	/**
	 * Add actions to this event to be triggered when conditions are met.
	 * 
	 * @param actions
	 *            One ore more events to add.
	 */
	public void addAction(GameEventAction... actions) {
		this.actions.addAll(Arrays.asList(actions));
	}

	public boolean isOneTimeEvent() {
		return isOneTimeEvent;
	}

	/**
	 * Get a list of parameters that should be set by the users.
	 * 
	 * @return A list of ParameterDescs. Each has a description() and a name()
	 *         method to get information on the fields.
	 */
	public List<ParameterDesc> getParameters() {
		List<ParameterDesc> paramDescriptions = new ArrayList<ParameterDesc>();
		for (Field field : this.getClass().getDeclaredFields()) {
			ParameterDesc paramDescription = field
					.getAnnotation(ParameterDesc.class);
			if (paramDescription != null) {
				paramDescriptions.add(paramDescription);
			}
		}
		return paramDescriptions;
	}

	protected void trigger(GameEventManager manager, GamePlayerGame gamePlayer) {
		for (GameEventAction action : actions) {
			action.act(manager, gamePlayer);
		}
	}

	protected abstract boolean eventHappened();

	private boolean shouldTriggerEvent() {
		return (isOneTimeEvent && !activated) || !isOneTimeEvent;
	}
}
