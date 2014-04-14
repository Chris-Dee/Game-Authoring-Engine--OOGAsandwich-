package gameplayer;

import gameEngine.Game;
import gameEngine.GameObject;
import gameEngine.GameObjectAction;
import gameEngine.Goal;
import gameEngine.InvalidEventActionException;
import gameEngine.InvalidEventException;
import gameEngine.Level;
import gameEngine.UninstantiatedGameObject;
import gameEngine.eventactions.RemoveAction;
import gameEngine.gameevents.TimerEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jgame.JGPoint;
import data.GameData;
import data.InvalidDataFileException;

public class MarioGame extends Game {

	public MarioGame() throws IOException, InvalidDataFileException,
			ClassNotFoundException {
		
		mediaTablePath = "mario.tbl";
		// game.screenSize = new JGPoint(640, 480);
		screenSize = new JGPoint(900, 900);

		Map<Integer, Tuple<String,Integer>> levelInputMap = new HashMap<Integer, Tuple<String,Integer>>();
		levelInputMap.put(39, new Tuple<String, Integer>("moveRight", 4));
		levelInputMap.put(37, new Tuple<String, Integer>("moveLeft",4));
		levelInputMap.put(38, new Tuple<String, Integer>("moveUp",12));
		levelInputMap.put(40, new Tuple<String, Integer>("moveDown",6));
		Map<Integer, Tuple<String,Integer>> otherInputMap = new HashMap<Integer, Tuple<String,Integer>>();
		otherInputMap.put(65, new Tuple<String, Integer>("moveLeft",4));
		otherInputMap.put(68, new Tuple<String, Integer>("moveRight",4));
		otherInputMap.put(87, new Tuple<String, Integer>("moveUp",8));
		int[][] modMatrix = { { 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, -1, 0, 1, 0, 0 } };
		collisionRules.add(new BasicCollision(1, 2, new GameObjectModification(
				modMatrix, 1, 0)));
		collisionRules.add(new BasicCollision(4, 2, new GameObjectModification(
				modMatrix, 1, 0)));
		collisionTriggers.add(new TriggerCollision("endlevel", 8, 1));
		collisionTriggers.add(new TriggerCollision("reset", 1, 4));

		List<UninstantiatedGameObject> objs = new ArrayList<UninstantiatedGameObject>();

		int objectID = 0;
		objs.add(new UninstantiatedGameObject("player", new JGPoint(50, 500),1, "mario", levelInputMap, false, true, objectID)); 
		for(int i = 0; i < 900 /32; i++){
			objectID++;
			objs.add(new UninstantiatedGameObject("brick", new  JGPoint(i * 32, 550), 2, "brick", true, objectID));
		} 
		objs.add(new UninstantiatedGameObject("moving platform", new JGPoint(200, 400), 2, "mobile", "pace", 125, 2, true, objectID+1)); 
		objs.add(new UninstantiatedGameObject("stationary platform", new JGPoint(590,350), 2, "stationary", true, objectID+2));
		objs.add(new UninstantiatedGameObject("turtle", new JGPoint(300, 450), 4,"turtle", "pace", 55, 2, false, objectID+3)); 
		objs.add(new UninstantiatedGameObject("luigi", new JGPoint(600, 450), 4, "luigi",otherInputMap, false, objectID+4)); 
		objs.add(new UninstantiatedGameObject("mushroom", new JGPoint(800, 400), 8, "mushroom", true, objectID+5)); // This code will eventually be used to parse the data.


		Level firstLevel = new Level("first level", new JGPoint(4, 4), objs, "skyblue", .6);//,new LevelInput(levelInputMap));
		
		
		List<UninstantiatedGameObject> objs2 = new ArrayList<UninstantiatedGameObject>();
		objectID = 0;
		objs2.add(new UninstantiatedGameObject("player", new JGPoint(50, 500),1, "mario", levelInputMap, false, true, objectID)); 
		for(int i = 0; i < 900 /32; i++){
			objectID++;
			objs2.add(new UninstantiatedGameObject("brick", new  JGPoint(i * 32, 750), 2, "brick", true, objectID));
		} 
		objs2.add(new UninstantiatedGameObject("moving platform", new JGPoint(200, 600), 2, "mobile", "pace", 125, 2, true, objectID+1)); 
		objs2.add(new UninstantiatedGameObject("stationary platform", new JGPoint(590,350), 2, "stationary", true, objectID+2));
		objs2.add(new UninstantiatedGameObject("turtle", new JGPoint(300, 750), 4,"turtle", "pace", 55, 2, false, objectID+3)); 
		objs2.add(new UninstantiatedGameObject("luigi", new JGPoint(600, 450), 4, "luigi",otherInputMap, false, objectID+4)); 
		objs2.add(new UninstantiatedGameObject("mushroom", new JGPoint(800, 400), 8, "mushroom", true, objectID+5)); // This code will eventually be used to parse the data.

		
		objs2.add(new UninstantiatedGameObject("moving platform", new JGPoint(100, 200), 2, "mobile", "pace", 125, 2, true, objectID+6)); 
		Level secondLevel = new Level("second level ya bish", new JGPoint(4, 4), objs2, "spacebackground", .6);
		
		try {
			TimerEvent event = new TimerEvent(-1, new ArrayList<Integer>(), new ArrayList<String>(Arrays.asList("50")));
			try {
				RemoveAction action = new RemoveAction(new ArrayList<Integer>(Arrays.asList(0)), new ArrayList<String>());
				event.addAction(action);
			} catch (InvalidEventActionException e) {
				e.printStackTrace();
			}
			firstLevel.addEvent(event);
		} catch (InvalidEventException e) {
			e.printStackTrace();
		}
		
		List<Level> myLevels = new ArrayList<Level>();
		myLevels.add(0,firstLevel);
		myLevels.add(1,secondLevel);
		addListOfLevels(myLevels);
		setCurrentLevel(0);
		
	}
}