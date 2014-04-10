package gameplayer;

import gameEngine.Game;
import gameEngine.GameForce;
import gameEngine.GameObject;
import gameEngine.GameObjectAction;
import gameEngine.Goal;
import gameEngine.Level;
import gameEngine.UninstantiatedGameObject;

import java.io.IOException;
import java.util.ArrayList;
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

		Map<Integer, String> levelInputMap = new HashMap<Integer, String>();
		levelInputMap.put(39, "moveRight");
		levelInputMap.put(37, "moveLeft");
		levelInputMap.put(38, "moveUp");
		levelInputMap.put(40, "moveDown");
		Map<Integer, String> otherInputMap = new HashMap<Integer, String>();
		otherInputMap.put(65, "moveLeft");
		otherInputMap.put(68, "moveRight");
		otherInputMap.put(87, "moveUp");
		int[][] modMatrix = { { 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, -1, 0, 1, 0, 0 } };
		collisionRules.add(new BasicCollision(1, 2, new GameObjectModification(
				modMatrix, 1, 0)));
		collisionRules.add(new BasicCollision(4, 2, new GameObjectModification(
				modMatrix, 1, 0)));
		collisionTriggers.add(new TriggerCollision("endlevel", 8, 1));
		collisionTriggers.add(new TriggerCollision("reset", 1, 4));

		 List<UninstantiatedGameObject> objs = new ArrayList<UninstantiatedGameObject>();

		
		  objs.add(new UninstantiatedGameObject("player", new JGPoint(50, 500),1, "mario", levelInputMap, false, true)); 
		  for(int i = 0; i < 900 /32; i++){
		   objs.add(new UninstantiatedGameObject("brick", new  JGPoint(i * 32, 550), 2, "brick", true)); } objs.add(new
		  UninstantiatedGameObject("moving platform", new JGPoint(200, 400), 2, "mobile", "pace", 125, 2, true)); objs.add(new
		  UninstantiatedGameObject("stationary platform", new JGPoint(590,350), 2, "stationary", true)); objs.add(new
		  UninstantiatedGameObject("turtle", new JGPoint(300, 450), 4,"turtle", "pace", 55, 2, false)); objs.add(new
		  UninstantiatedGameObject("luigi", new JGPoint(600, 450), 4, "luigi",otherInputMap, false)); objs.add(new UninstantiatedGameObject("goal",
		  new JGPoint(800, 400), 8, "mushroom", true)); // This code will eventually be used to parse the data.
		  List<GameForce> forces = new ArrayList<GameForce>(); 
		  GameForce force1 = new GameForce(); forces.add(force1);

		Level firstLevel = new Level("first level", new JGPoint(4, 4), objs, forces, "skyblue", .4);//,new LevelInput(levelInputMap));
		
		List<Level> myLevels = new ArrayList<Level>();
		myLevels.add(firstLevel);
		addListOfLevels(myLevels);
		setCurrentLevel(myLevels.get(0));
		
	}
}