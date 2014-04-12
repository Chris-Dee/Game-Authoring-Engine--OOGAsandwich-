package gameplayer;

import gameEngine.GameEvent;
import gameEngine.GameObject;

import java.util.List;

public class GameEventManager {
	List<GameEvent> gameEvents;
	List<GameObject> gameObjects;

	public GameEventManager(List<GameObject> gameObjects,
			List<GameEvent> gameEvents) {
		this.gameObjects = gameObjects;
		this.gameEvents = gameEvents;
	}

	public void check() {
		for (GameEvent event : gameEvents) {
			event.check();
		}
	}
}
