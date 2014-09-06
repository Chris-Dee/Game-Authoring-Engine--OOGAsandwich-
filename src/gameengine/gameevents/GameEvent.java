package gameengine.gameevents;

import gameengine.eventactions.GameEventAction;
import gameplayer.GameEventManager;
import gameplayer.GamePlayerEngine;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GameEvent {
	protected int sourceObjectId;
	protected List<Integer> recipientObjectIds;
	protected List<GameEventAction> actions;
	protected List<String> paramaters;
	protected boolean activated;
	protected boolean isOneTimeEvent;
	protected String myName;

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
	public GameEvent(List<String> parameters, String name)
			throws InvalidEventException {
		this(parameters, false);
		myName = name;
	}

	public GameEvent() throws InvalidEventException {
		this(new ArrayList<String>(), false);
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
	public GameEvent(List<String> parameters, boolean isOneTimeEvent)
			throws InvalidEventException {
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
	public boolean check(GameEventManager manager, GamePlayerEngine gamePlayer) {
		if (shouldTriggerEvent() && eventHappened(gamePlayer)) {
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
	 *            One or more events to add.
	 */
	public void addAction(GameEventAction... actions) {
		System.out.println(actions);
		System.out.println(this.actions);
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

	/**
	 * Returns the name of the event.
	 * 
	 * @return The name of this event.
	 */
	public String getName() {
		return myName;
	}

	protected void trigger(GameEventManager manager, GamePlayerEngine gamePlayer) {
		for (GameEventAction action : actions) {
			action.act(manager, gamePlayer);
		}
	}

	protected abstract boolean eventHappened(GamePlayerEngine gamePlayer);

	private boolean shouldTriggerEvent() {
		return (isOneTimeEvent && !activated) || !isOneTimeEvent;
	}
}
