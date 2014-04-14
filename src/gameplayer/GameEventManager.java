package gameplayer;

import gameEngine.GameEvent;
import gameEngine.GameObject;

import java.util.Iterator;
import java.util.List;

public class GameEventManager {
	protected List<GameEvent> gameEvents;
	protected List<GameObject> gameObjects;
	protected GamePlayerGUI gamePlayer;

	public GameEventManager(List<GameObject> gameObjects,
			List<GameEvent> gameEvents, GamePlayerGUI gamePlayer) {
		this.gameObjects = gameObjects;
		this.gameEvents = gameEvents;
		this.gamePlayer = gamePlayer;
	}

	public void check() {
		Iterator<GameEvent> eventIterator = gameEvents.iterator();
		while (eventIterator.hasNext()) {
			GameEvent event = eventIterator.next();
			if (event.check(this, gamePlayer) && event.isOneTimeEvent()) {
				eventIterator.remove();
			}
		}
	}

	public GameObject getObjectById(int id) {
		// TODO: Use map
		for (GameObject object : gameObjects) {
			if (object.getID() == id) {
				return object;
			}
		}
		return null;
	}
}
