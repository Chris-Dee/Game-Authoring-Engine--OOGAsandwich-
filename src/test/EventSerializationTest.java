package test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import gameengine.Level;
import gameengine.eventactions.GameEventAction;
import gameengine.eventactions.InvalidEventActionException;
import gameengine.eventactions.RemoveObjectAction;
import gameengine.gameevents.GameEvent;
import gameengine.gameevents.InvalidEventException;
import gameengine.gameevents.TimerEvent;

import org.junit.Before;
import org.junit.Test;

import data.GameData;
import data.InvalidDataFileException;
import data.JsonReader;
import data.GenericJsonAdapter;
import data.PropertiesReader;
import data.SandwichGameData;

public class EventSerializationTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void canSerializeThenDeserialize() throws InvalidDataFileException,
			InvalidEventException, ClassNotFoundException,
			InvalidEventActionException {
		GameData gd = new SandwichGameData();
		Level level = new Level("");
		GameEvent event = new TimerEvent();
		event.addAction(new RemoveObjectAction(new ArrayList<String>(Arrays
				.asList("1", "2"))));
		level.addEvent(event);
		gd.addObj(level);

		PropertiesReader jsonReader = new JsonReader(gd.toString(),
				new GenericJsonAdapter<GameEventAction>(GameEventAction.class,
						"gameengine.eventactions"),
				new GenericJsonAdapter<GameEvent>(GameEvent.class,
						"gameengine.gameevents"));
		Map<String, List<Object>> deserialized = jsonReader.parse();
		Level deserializedLevel = (Level) deserialized.get("gameengine.Level")
				.get(0);
		GameEvent deserializedEvent = deserializedLevel.getEvents().get(0);
		assertEquals(deserializedEvent.getClass(), TimerEvent.class);
	}

}
