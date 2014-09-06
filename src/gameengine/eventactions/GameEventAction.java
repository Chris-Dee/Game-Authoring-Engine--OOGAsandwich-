package gameengine.eventactions;

import gameengine.gameevents.ParameterDesc;
import gameplayer.GameEventManager;
import gameplayer.GamePlayerEngine;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class GameEventAction {
	protected List<String> arguments;

	public GameEventAction() {
	}

	public GameEventAction(List<String> arguments)
			throws InvalidEventActionException {
		this.arguments = arguments;
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
	 * Perform this action.
	 * 
	 * @param manager
	 *            The event manager, use this to get references to objects from
	 *            their IDs.
	 * @param gamePlayer
	 *            The GamePlayerEngine, use this to perform actions on the game.
	 */
	public abstract void act(GameEventManager manager,
			GamePlayerEngine gamePlayer);

}
