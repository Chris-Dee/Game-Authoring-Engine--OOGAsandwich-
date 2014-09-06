package data;

import gameengine.eventactions.GameEventAction;
import gameengine.gameevents.GameEvent;
import gameengine.powerups.PowerUp;

public class SandwichGameData extends GameData {

	public SandwichGameData(String filename) {
		super(filename, new GenericJsonAdapter<GameEventAction>(
				GameEventAction.class, "gameengine.eventactions"),
				new GenericJsonAdapter<GameEvent>(GameEvent.class,
						"gameengine.gameevents"),
				new GenericJsonAdapter<PowerUp>(PowerUp.class,
						"gameengine.powerups"));
	}

	public SandwichGameData() {
		super("", new GenericJsonAdapter<GameEventAction>(
				GameEventAction.class, "gameengine.eventactions"),
				new GenericJsonAdapter<GameEvent>(GameEvent.class,
						"gameengine.gameevents"),
				new GenericJsonAdapter<PowerUp>(PowerUp.class,
						"gameengine.powerups"));
	}

}
