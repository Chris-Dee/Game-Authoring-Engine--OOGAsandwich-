package gameplayer;

import gameengine.GameObject;
import gameengine.gameevents.GameEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GameEventManager {
	protected List<GameEvent> gameEvents;
	protected Map<Integer, GameObject> uniqueIdToObject;
	protected Map<Integer, List<GameObject>> colIdToObject;
	protected GamePlayerEngine gamePlayer;

	public GameEventManager(List<GameObject> gameObjects,
			List<GameEvent> gameEvents, GamePlayerEngine gamePlayer) {
		uniqueIdToObject = new HashMap<Integer, GameObject>();
		colIdToObject = new HashMap<Integer, List<GameObject>>();
		for (GameObject obj : gameObjects) {
			if (!colIdToObject.containsKey(obj.getColID())) {
				colIdToObject.put(obj.getColID(), new ArrayList<GameObject>());
			}
			colIdToObject.get(obj.getColID()).add(obj);
			uniqueIdToObject.put(obj.getID(), obj);
		}
		this.gameEvents = gameEvents;
		this.gamePlayer = gamePlayer;
	}

	/**
	 * Check all events to see if they have happened. Their actions are executed
	 * if the conditions for the event to happen are satisfied.
	 */
	public void check() {
		Iterator<GameEvent> eventIterator = gameEvents.iterator();
		while (eventIterator.hasNext()) {
			GameEvent event = eventIterator.next();
			if (event.check(this, gamePlayer) && event.isOneTimeEvent()) {
				eventIterator.remove();
			}
		}
	}

	/**
	 * Get all GameObjects with the specified collision ID.
	 * 
	 * @param id
	 *            A collision ID.
	 * @return A List of GameObject with collision id "id".
	 */
	public List<GameObject> getObjectsByColId(int id) {
		if (colIdToObject.containsKey(id)) {
			return colIdToObject.get(id);
		}
		return new ArrayList<GameObject>();
	}

	/**
	 * Get a GameObject by its unique ID.
	 * 
	 * @param id
	 *            A unique ID.
	 * @return A GameObject with unique id "id", or null if no GameObject has
	 *         the specified id.
	 */
	public GameObject getObjectByUniqueId(int id) {
		if (uniqueIdToObject.containsKey(id)) {
			return uniqueIdToObject.get(id);
		}
		return null;
	}
}
