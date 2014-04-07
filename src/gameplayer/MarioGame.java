package gameplayer;

import gameEngine.Game;
import gameEngine.GameForce;
import gameEngine.GameObject;
import gameEngine.GameObjectAction;
import gameEngine.Goal;
import gameEngine.Level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jgame.JGPoint;
import data.InvalidDataFileException;

public class MarioGame extends Game{
	

	public MarioGame() throws IOException, InvalidDataFileException{
		mediaTablePath = "mario.tbl";
		//game.screenSize = new JGPoint(640, 480);
		screenSize = new JGPoint(900, 900);
		
		Map<Integer, String> levelInputMap = new HashMap<Integer, String>();
		levelInputMap.put(39, "moveRight");
		levelInputMap.put(37, "moveLeft");
		levelInputMap.put(38, "moveUp");
		levelInputMap.put(40, "moveDown");
		int[][] modMatrix = {{1, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, -1, 0, 1, 0, 0}};
		collisionRules.add(
				new BasicCollision(1, 2,
						new GameObjectModification(modMatrix, 1, 0)));
		collisionRules.add(
				new BasicCollision(4, 2,
						new GameObjectModification(modMatrix, 1, 0)));
		collisionTriggers.add(
				new TriggerCollision(8, 1));
		List<GameObject> objs = new ArrayList<GameObject>();

		objs.add(new GameObject("player", new JGPoint(50, 500), 1, "mario", levelInputMap, false));
		//objs.add(new GameObject("test", new JGPoint(100, 100), 1, "hero-r", new GameObjectAction("pace",25, 5)));
		//objs.add(new GameObject("test", new JGPoint(20, 105), 1, "hero-r", new GameObjectAction(4,1)));
		for(int i = 0; i < 900 / 32; i++){
			objs.add(new GameObject("brick", new JGPoint(i * 32, 550), 2, "brick", true));
		}
		objs.add(new GameObject("moving platform", new JGPoint(200, 400), 2, "mobile", "pace", 125, 2, true));
		objs.add(new GameObject("stationary platform", new JGPoint(590, 350), 2, "stationary", true));
		objs.add(new GameObject("turtle", new JGPoint(300, 450), 4, "turtle", "pace", 55, 2, false));
		objs.add(new GameObject("goal", new JGPoint(800, 400), 8, "mushroom", true));
		// This code will eventually be used to parse the data.
		
//		myGameData.setFileName("uninstantiatedgameobjectsfile");
//		Map<String, List<Object>> myObjectMap = myGameData.parse();
//		for (String str : myObjectMap.keySet()) {
//			for (Object obj : myObjectMap.get(str)) {
//				objs.add((UninstantiatedGameObject) obj);
//			}
//		}
		
		List<GameForce> forces = new ArrayList<GameForce>();
		GameForce force1 = new GameForce();
		forces.add(force1);
		
	
		
		Level firstLevel = new Level("first level", new JGPoint(640, 480), objs, forces, "skyblue", .4);//,new LevelInput(levelInputMap));

		
		
		addLevel(firstLevel);
		setCurrentLevel(firstLevel);
	}
}
